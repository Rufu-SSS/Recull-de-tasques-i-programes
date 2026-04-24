#nullable enable
using System;
using System.IO;
using IOFile = System.IO.File;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Telegram.Bot;
using Telegram.Bot.Polling;
using Telegram.Bot.Types;
using Telegram.Bot.Types.Enums;

class Program
{
    private static TelegramBotClient botClient = null!;
    private static readonly Dictionary<long, DateTime> lastCommand = new();

    private static readonly Dictionary<string, Func<long, string[], CancellationToken, Task>> commands
        = new();

    static async Task Main(string[] args)
    {
        LoadEnv();

        string? token = Environment.GetEnvironmentVariable("API_KEY");
        if (string.IsNullOrWhiteSpace(token))
        {
            Console.WriteLine("API_KEY no trobada");
            return;
        }

        botClient = new TelegramBotClient(token);

        RegisterCommands();

        var me = await botClient.GetMeAsync();
        Console.WriteLine($"Bot actiu: {me.Username}");

        using var cts = new CancellationTokenSource();

        botClient.StartReceiving(
            HandleUpdateAsync,
            HandleErrorAsync,
            new ReceiverOptions { AllowedUpdates = Array.Empty<UpdateType>() },
            cts.Token
        );

        Console.WriteLine("En marxa...");
        Console.ReadLine();
        cts.Cancel();
    }

    private static void LoadEnv()
    {
        if (!IOFile.Exists(".env")) return;

        foreach (var line in IOFile.ReadAllLines(".env"))
        {
            if (string.IsNullOrWhiteSpace(line) || line.StartsWith("#"))
                continue;

            int idx = line.IndexOf('=');
            if (idx == -1) continue;

            Environment.SetEnvironmentVariable(
                line[..idx].Trim(),
                line[(idx + 1)..].Trim()
            );
        }
    }

    private static void RegisterCommands()
    {
        commands["/hola"] = async (chatId, args, ct) =>
        {
            await botClient.SendTextMessageAsync(chatId, "Hola", cancellationToken: ct);
        };

        commands["/dia"] = async (chatId, args, ct) =>
        {
            await botClient.SendTextMessageAsync(
                chatId,
                DateTime.Now.ToString("yyyy-MM-dd"),
                cancellationToken: ct
            );
        };

        commands["/hora"] = async (chatId, args, ct) =>
        {
            var tz = TimeZoneInfo.FindSystemTimeZoneById("Europe/Madrid");
            var time = TimeZoneInfo.ConvertTime(DateTime.UtcNow, tz);

            await botClient.SendTextMessageAsync(
                chatId,
                time.ToString("HH:mm:ss"),
                cancellationToken: ct
            );
        };

        commands["/ajuda"] = async (chatId, args, ct) =>
        {
            await botClient.SendTextMessageAsync(
                chatId,
                "/hola\n/dia\n/hora\n/ajuda",
                cancellationToken: ct
            );
        };
    }

    private static async Task HandleUpdateAsync(ITelegramBotClient bot, Update update, CancellationToken ct)
    {
        var message = update.Message;
        if (message?.Text == null) return;

        long chatId = message.Chat.Id;
        string msg = message.Text.Trim();

        if (!msg.StartsWith("/")) return;

        Console.WriteLine($"[{DateTime.Now:HH:mm:ss}] {chatId}: {msg}");

        if (IsSpam(chatId)) return;

        var parts = msg.Split(' ', StringSplitOptions.RemoveEmptyEntries);
        string command = parts[0].ToLower();
        string[] args = parts.Length > 1 ? parts[1..] : Array.Empty<string>();

        if (commands.TryGetValue(command, out var handler))
        {
            await handler(chatId, args, ct);
        }
    }

    private static bool IsSpam(long chatId)
    {
        if (lastCommand.TryGetValue(chatId, out var last) &&
            (DateTime.UtcNow - last).TotalMilliseconds < 800)
        {
            return true;
        }

        lastCommand[chatId] = DateTime.UtcNow;
        return false;
    }

    private static Task HandleErrorAsync(ITelegramBotClient bot, Exception ex, CancellationToken ct)
    {
        Console.WriteLine($"Error: {ex.Message}");
        return Task.CompletedTask;
    }
}