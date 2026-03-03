// GameManager.cs — afegeix-lo a un GameObject buit "GameManager"
using UnityEditor;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance;
    public GameObject playerPrefab;

    void Awake()
    {
        if (Instance != null)
        {
            Destroy(gameObject);
            return;
        }
        Instance = this;
        DontDestroyOnLoad(gameObject);
    }

    void OnEnable()
    {
        SceneManager.sceneLoaded += OnSceneLoaded;
    }

    void OnDisable()
    {
        SceneManager.sceneLoaded -= OnSceneLoaded;
    }

    void OnSceneLoaded(Scene scene, LoadSceneMode mode)
    {
        if (scene.name == "MainMenu") return;

        GameObject spawnPoint = GameObject.Find("PlayerSpawnPoint");
        Vector3 pos = spawnPoint != null ? spawnPoint.transform.position : Vector3.zero;

        GameObject existingPlayer = GameObject.FindGameObjectWithTag("Player");
        if (existingPlayer != null)
        {
            existingPlayer.transform.position = pos;
            return;
        }

        GameObject player = Instantiate(playerPrefab, pos, Quaternion.identity);

        SceneManager.MoveGameObjectToScene(player, scene);

        Debug.Log("Player instanciat a: " + pos);
    }

}