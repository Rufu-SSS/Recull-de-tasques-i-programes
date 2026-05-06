using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{
    private bool isGameOver = false;

    void Update()
    {
        // Reiniciar amb tecla R durant el Game Over
        if (isGameOver && Input.GetKeyDown(KeyCode.R))
        {
            Restart();
        }
    }

    public void GameOver()
    {
        if (isGameOver) return;
        isGameOver = true;
        Time.timeScale = 0f;
        Debug.Log("Game Over - Prem R per reiniciar");
    }

    public void Restart()
    {
        isGameOver = false;
        Time.timeScale = 1f;
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }
}