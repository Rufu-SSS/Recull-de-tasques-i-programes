using UnityEngine;
using UnityEngine.UI;

public class DifficultyButton : MonoBehaviour
{
    private Button _button;
    private GameManager _gameManager;
    public int difficulty;

    void Start()
    {
        _button = GetComponent<Button>();
        _gameManager = GameObject.Find("GameManager")?.GetComponent<GameManager>();

        if (_gameManager == null)
            Debug.LogError("No es troba el GameManager!");
        else
            _button.onClick.AddListener(SetDifficulty);
    }

    void SetDifficulty()
    {
        Debug.Log(_button.gameObject.name + " was clicked");
        _gameManager.StartGame(difficulty);
    }
}