#nullable enable
using System;
using System.IO;
using IOFile = System.IO.File;
using System.Net.Http;
using System.Text.Json;
using System.Threading;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Linq;
using Telegram.Bot;
using Telegram.Bot.Polling;
using Telegram.Bot.Types;
using Telegram.Bot.Types.Enums;

class Program
{
    private static TelegramBotClient botClient = null!;
    private static readonly HttpClient httpClient = new HttpClient();

    static async Task Main(string[] args)
    {
        foreach (var line in IOFile.ReadAllLines(".env"))
        {
            if (string.IsNullOrWhiteSpace(line) || line.StartsWith("#"))
                continue;

            int idx = line.IndexOf('=');
            if (idx == -1) continue;

            string key = line[..idx].Trim();
            string value = line[(idx + 1)..].Trim();

            Environment.SetEnvironmentVariable(key, value);
        }

        string? token = Environment.GetEnvironmentVariable("API_KEY");

        if (string.IsNullOrEmpty(token))
        {
            Console.WriteLine("API_KEY no trobada al .env");
            return;
        }

        botClient = new TelegramBotClient(token);

        var me = await botClient.GetMeAsync();
        Console.WriteLine($"Bot id: {me.Id}, Bot Name: {me.FirstName}");

        using var cts = new CancellationTokenSource();

        botClient.StartReceiving(
            HandleUpdateAsync,
            HandleErrorAsync,
            new ReceiverOptions { AllowedUpdates = Array.Empty<UpdateType>() },
            cts.Token
        );

        Console.WriteLine("Bot en marxa...");
        Console.ReadKey();
        cts.Cancel();
    }

    private static async Task HandleUpdateAsync(ITelegramBotClient bot, Update update, CancellationToken ct)
    {
        if (update.Message?.Text == null) return;

        Console.WriteLine($"MSG: {update.Message.Text}");

        string msg = update.Message.Text.Trim();
        long chatId = update.Message.Chat.Id;

        if (!msg.StartsWith("/commander")) return;

        string query = msg.Replace("/commander", "").Trim();

        if (string.IsNullOrWhiteSpace(query))
        {
            await bot.SendTextMessageAsync(chatId,
                "Escriu un commander.\nEx: /commander atraxa",
                cancellationToken: ct);
            return;
        }

        var card = await GetCommanderImage(query);

        if (card == null)
        {
            await bot.SendTextMessageAsync(chatId,
                $"No s'ha trobat: {query}",
                cancellationToken: ct);
            return;
        }

        await bot.SendPhotoAsync(
            chatId,
            InputFile.FromUri(card.ImageUrl),
            caption: $"⭐ {card.Name}",
            cancellationToken: ct
        );
    }

    // =========================
    // 🔥 CORE SEARCH LOGIC
    // =========================

    private static async Task<CardResult?> GetCommanderImage(string query)
    {
        return await TryFuzzy(query)
            ?? await TryExact(query)
            ?? await TrySearch(query);
    }

    private static async Task<CardResult?> TryFuzzy(string query)
    {
        var url = $"https://api.scryfall.com/cards/named?fuzzy={Uri.EscapeDataString(query)}";
        return await FetchCard(url);
    }

    private static async Task<CardResult?> TryExact(string query)
    {
        var url = $"https://api.scryfall.com/cards/named?exact={Uri.EscapeDataString(query)}";
        return await FetchCard(url);
    }

    private static async Task<CardResult?> TrySearch(string query)
    {
        var url = $"https://api.scryfall.com/cards/search?q=name:{Uri.EscapeDataString(query)}&unique=cards";

        var res = await httpClient.GetAsync(url);
        if (!res.IsSuccessStatusCode) return null;

        var json = await res.Content.ReadAsStringAsync();
        using var doc = JsonDocument.Parse(json);

        if (!doc.RootElement.TryGetProperty("data", out var data))
            return null;

        var card = data.EnumerateArray().FirstOrDefault();
        if (card.ValueKind == JsonValueKind.Undefined)
            return null;

        return ExtractCard(card);
    }

    private static async Task<CardResult?> FetchCard(string url)
    {
        var res = await httpClient.GetAsync(url);
        if (!res.IsSuccessStatusCode)
            return null;

        var json = await res.Content.ReadAsStringAsync();
        using var doc = JsonDocument.Parse(json);

        return ExtractCard(doc.RootElement);
    }

    private static CardResult? ExtractCard(JsonElement card)
    {
        string name = card.GetProperty("name").GetString() ?? "";
        string image = "";

        if (card.TryGetProperty("image_uris", out var img))
        {
            image = img.GetProperty("normal").GetString() ?? "";
        }
        else if (card.TryGetProperty("card_faces", out var faces))
        {
            var face = faces[0];
            if (face.TryGetProperty("image_uris", out var faceImg))
                image = faceImg.GetProperty("normal").GetString() ?? "";
        }

        if (string.IsNullOrWhiteSpace(image))
            return null;

        return new CardResult
        {
            Name = name,
            ImageUrl = image
        };
    }

    private static Task HandleErrorAsync(ITelegramBotClient bot, Exception ex, CancellationToken ct)
    {
        Console.WriteLine($"Error: {ex.Message}");
        return Task.CompletedTask;
    }
}

class CardResult
{
    public string Name { get; set; } = "";
    public string ImageUrl { get; set; } = "";
}