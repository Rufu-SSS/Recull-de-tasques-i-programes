using System;
using System.IO;
using System.Collections.Generic;
using UnityEngine;

[Serializable]
public class PlayerScore
{
    public string name;
    public int score;
}

[Serializable]
public class SaveData
{
    public List<PlayerScore> scores = new List<PlayerScore>();
    public int highScore;
}

public class SaveManager : MonoBehaviour
{
    private string _filePath;
    private SaveData _data = new SaveData();

    void Awake()
    {
        _filePath = Path.Combine(Application.persistentDataPath, "savegame.json");
    }

    public void SaveGame(string playerName, int score)
    {
        // Afegeix o actualitza la puntuació del jugador
        PlayerScore existing = _data.scores.Find(p => p.name == playerName);
        if (existing != null)
        {
            existing.score = score;
        }
        else
        {
            _data.scores.Add(new PlayerScore { name = playerName, score = score });
        }

        // Actualitza el highscore
        if (score > _data.highScore)
            _data.highScore = score;

        string json = JsonUtility.ToJson(_data, true);
        File.WriteAllText(_filePath, json);
        Debug.Log($"Guardat! Jugador: {playerName} | Score: {score} | Fitxer: {_filePath}");
    }

    public SaveData LoadGame()
    {
        if (!File.Exists(_filePath))
        {
            Debug.LogWarning("No hi ha fitxer de guardat.");
            return null;
        }

        string json = File.ReadAllText(_filePath);
        _data = JsonUtility.FromJson<SaveData>(json);
        Debug.Log($"Carregat! {_data.scores.Count} jugadors, HighScore: {_data.highScore}");
        return _data;
    }

    public void ResetData()
    {
        if (File.Exists(_filePath))
        {
            File.Delete(_filePath);
            _data = new SaveData();
            Debug.Log("Dades esborrades.");
        }
        else
            Debug.LogWarning("No hi ha res a esborrar.");
    }

    public int GetScore(string playerName)
    {
        PlayerScore p = _data.scores.Find(p => p.name == playerName);
        return p != null ? p.score : 0;
    }
}