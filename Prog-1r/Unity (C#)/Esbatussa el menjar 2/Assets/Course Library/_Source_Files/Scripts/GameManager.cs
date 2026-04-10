using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public List<GameObject> targets;
    public float spawnRate = 1.0f;
    private int _score;
    public int Score => _score;
    private int lives = 3;
    public TextMeshProUGUI scoreText;
    public TextMeshProUGUI livesText;
    public TextMeshProUGUI gameOverText;

    public bool isGameActive;
    public Button restartButton;
    public GameObject titleScreen;

    // Comprova que els components UI estan assignats; si no, intenta trobar-los pel nom (assegura't que els noms coincideixin)
    void Awake()
    {
        if (scoreText == null)
            scoreText = GameObject.Find("ScoreText").GetComponent<TextMeshProUGUI>();
        if (livesText == null)
            livesText = GameObject.Find("livesText").GetComponent<TextMeshProUGUI>();
        if (gameOverText == null)
            gameOverText = GameObject.Find("GameOverText").GetComponent<TextMeshProUGUI>();
        if (restartButton == null)
            restartButton = GameObject.Find("RestartButton").GetComponent<Button>();
        if (titleScreen == null)
            titleScreen = GameObject.Find("TitleScreen");
    }

    // Start is called before the first frame update
    void Start() { }

    public void StartGame(int difficulty)
    {
        spawnRate /= difficulty;
        isGameActive = true;
        lives = 3;
        _score = 0; //reseteja el score aquí explícitament
        UpdateLives(0);
        StartCoroutine(SpawnTarget());
        UpdateScore(0);
        if (titleScreen != null)
            titleScreen.SetActive(false);
    }

    IEnumerator SpawnTarget()
    {
        while (isGameActive)
        {
            yield return new WaitForSeconds(spawnRate);
            int index = Random.Range(0, targets.Count);
            Instantiate(targets[index]);
        }
    }

    public void UpdateScore(int scoreToAdd)
    {
        _score += scoreToAdd;
        if (scoreText != null)
            scoreText.text = "Score: " + _score;
    }

    // Aquí restem o sumem vides segons el valor passat; pots adaptar la lògica segons el que vulguis aconseguir.
    public void UpdateLives(int quantitat)
    {
        lives -= quantitat;
        if (livesText != null)
            livesText.text = "HP: " + lives;
        // Exemple: si les vides arriben a 0, acaba el joc
        if (lives <= 0)
            GameOver();
    }

    public void GameOver()
    {
        isGameActive = false;
        if (gameOverText != null) gameOverText.gameObject.SetActive(true);
        if (restartButton != null) restartButton.gameObject.SetActive(true);
        Time.timeScale = 0; // al final del tot
    }

    public void RestartGame()
    {
        Time.timeScale = 1;
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
    }

    // Update is called once per frame
    void Update() { }
}
