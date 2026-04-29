#nullable enable
using System;
using System.IO;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;
using Telegram.Bot;
/*
enviar missatges a ids concrets quan hi hagi problemes
*/
class Program
{
    private static TelegramBotClient botClient = null!;
    private static string LogFile = "log.txt";

    private static readonly HttpClient httpClient = new HttpClient();
    private static long alertChatId;
    private static readonly string[] Urls = {
        "https://mail.google.com/",
    };

    static async Task Main(string[] args)
    {
        Log("=== INICI ===");
        LoadEnv();

        string? token = Environment.GetEnvironmentVariable("API_KEY");
        string? chatIdEnv = Environment.GetEnvironmentVariable("CHAT_ID");
        string? alertChatIdEnv = Environment.GetEnvironmentVariable("CHAT_ID_ALERT");
        if (!string.IsNullOrWhiteSpace(alertChatIdEnv) && long.TryParse(alertChatIdEnv, out long parsedAlert))
        {
            alertChatId = parsedAlert;
            Log($"AlertChatId: {alertChatId}");
        }

        if (string.IsNullOrWhiteSpace(token) || string.IsNullOrWhiteSpace(chatIdEnv))
        {
            Log("ERROR: Config incorrecta (API_KEY o CHAT_ID buits)");
            return;
        }

        if (!long.TryParse(chatIdEnv, out long chatId))
        {
            Log("ERROR: CHAT_ID invalid");
            return;
        }

        Log("Config OK");

        try
        {
            botClient = new TelegramBotClient(token);
            httpClient.Timeout = TimeSpan.FromSeconds(15);
            if (!httpClient.DefaultRequestHeaders.Contains("User-Agent"))
            {
                httpClient.DefaultRequestHeaders.Add(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/124.0.0.0 Safari/537.36"
                );
            }

            foreach (var url in Urls)
                await RunCheck(chatId, url);
        }
        catch (Exception ex)
        {
            Log($"EXCEPCIO global: {ex.Message}");
        }

        Log("=== FI ===");
    }

    static async Task RunCheck(long chatId, string url)
    {
        Log($"Comprovant: {url}");

        bool ok = await CheckWebWithRetry(url);
        Log($"Resultat: {(ok ? "OK" : "CAIGUT")}");

        string safeUrl = url.Replace("https://", "").Replace("/", "_").TrimEnd('_');

        // sanititzar caràcters no vàlids
        foreach (var c in Path.GetInvalidFileNameChars())
        {
            safeUrl = safeUrl.Replace(c, '_');
        }
        string stateFile = $"state_{safeUrl}.txt";

        if (!File.Exists(stateFile))
        {
            Log("Primera execucio, guardant estat inicial i avisant");
            File.WriteAllText(stateFile, $"{(ok ? "OK" : "FAIL")}|{DateTime.Now:o}");
            await botClient.SendTextMessageAsync(chatId,
                $"Monitor actiu: {url}\nEstat inicial: {(ok ? "OK" : "CAIGUT")}");
            return;
        }

        string[] parts = File.ReadAllText(stateFile).Trim().Split('|');
        bool lastOk = parts[0] == "OK";
        Log($"Estat anterior: {(lastOk ? "OK" : "CAIGUT")}");

        string? alertChatIdEnv = Environment.GetEnvironmentVariable("CHAT_ID_ALERT");
        if (!string.IsNullOrWhiteSpace(alertChatIdEnv) && long.TryParse(alertChatIdEnv, out long parsedAlert))
        {
            alertChatId = parsedAlert;
            Log($"AlertChatId: {alertChatId}");
        }
        else
        {
            Log("Sense canvis, no s'envia res");
        }

        File.WriteAllText(stateFile, $"{(ok ? "OK" : "FAIL")}|{DateTime.Now:o}");
    }

    static async Task<bool> CheckWebWithRetry(string url, int intents = 3)
    {
        for (int i = 0; i < intents; i++)
        {
            if (i > 0)
            {
                Log($"Reintent {i}/{intents - 1}...");
                await Task.Delay(2000);
            }

            if (await CheckWeb(url)) return true;
        }
        return false;
    }

    static async Task<bool> CheckWeb(string url)
    {
        try
        {
            using var cts = new CancellationTokenSource(TimeSpan.FromSeconds(10));
            var response = await httpClient.GetAsync(url, cts.Token);
            Log($"HTTP status: {(int)response.StatusCode}");
            return response.IsSuccessStatusCode;
        }
        catch (TaskCanceledException)
        {
            Log("Error: Timeout");
            return false;
        }
        catch (HttpRequestException ex)
        {
            Log($"Error HTTP: {ex.Message}");
            return false;
        }
        catch (Exception ex)
        {
            Log($"Error inesperat: {ex.Message}");
            return false;
        }
    }

    static void Log(string msg)
    {
        string line = $"[{DateTime.Now:yyyy-MM-dd HH:mm:ss}] {msg}";
        Console.WriteLine(line);
        try
        {
            if (File.Exists(LogFile) && new FileInfo(LogFile).Length > 1_000_000)
                File.WriteAllText(LogFile, "");

            File.AppendAllText(LogFile, line + Environment.NewLine);
        }
        catch { }
    }

    static void LoadEnv()
    {
        if (!File.Exists(".env")) { Log(".env no trobat"); return; }
        foreach (var line in File.ReadAllLines(".env"))
        {
            if (string.IsNullOrWhiteSpace(line) || line.StartsWith("#")) continue;
            int idx = line.IndexOf('=');
            if (idx == -1) continue;
            Environment.SetEnvironmentVariable(line[..idx].Trim(), line[(idx + 1)..].Trim());
        }
        Log(".env carregat");
    }
}