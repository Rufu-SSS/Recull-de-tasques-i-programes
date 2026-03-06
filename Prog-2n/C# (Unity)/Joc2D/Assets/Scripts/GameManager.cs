// GameManager.cs — versió completa corregida
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance;
    public GameObject playerPrefab;

    private GameObject _playerInstance;

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
        if (scene.name == "MainMenu")
        {
            // Destrueix el player quan tornem al menú
            if (_playerInstance != null)
            {
                Destroy(_playerInstance);
                _playerInstance = null;
            }
            return;
        }

        GameObject spawnPoint = GameObject.Find("PlayerSpawnPoint");
        Vector3 pos = spawnPoint != null ? spawnPoint.transform.position : Vector3.zero;

        if (_playerInstance != null)
        {
            // El player ja existeix, només el reposiciona
            _playerInstance.transform.position = pos;
            SceneManager.MoveGameObjectToScene(_playerInstance, scene);
        }
        else
        {
            // Primera vegada, crea el player
            _playerInstance = Instantiate(playerPrefab, pos, Quaternion.identity);
            SceneManager.MoveGameObjectToScene(_playerInstance, scene);
        }
    }
}