using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour {
    public static GameManager Instance;
    public GameObject playerPrefab;
    private GameObject _playerInstance;
    void Awake() {
        if (Instance != null) {
            Destroy(gameObject);
            return;
        }
        Instance = this;
        DontDestroyOnLoad(gameObject);
    }
    private void OnEnable() {
        PlayerHealth.OnPlayerDied += HandleGameOver;
        SceneManager.sceneLoaded += OnSceneLoaded;
    }
    private void OnDisable() {
        PlayerHealth.OnPlayerDied -= HandleGameOver;
        SceneManager.sceneLoaded -= OnSceneLoaded;
    }
    private void HandleGameOver() {
        if (_playerInstance == null) return;
        _playerInstance.SetActive(true);
        GameObject spawnPoint = GameObject.Find("PlayerSpawnPoint");
        Vector3 pos = spawnPoint != null ? spawnPoint.transform.position : Vector3.zero;
        _playerInstance.transform.position = pos;

        Rigidbody2D rb = _playerInstance.GetComponent<Rigidbody2D>();
        rb.simulated = true;
        rb.linearVelocity = Vector2.zero; // ← important, reset velocitat acumulada
        _playerInstance.GetComponent<PlayerMove>().enabled = true;
        PlayerHealth ph = _playerInstance.GetComponent<PlayerHealth>();
        if (ph != null) ph.currentHealth = ph.maxHealth;
    }
    void OnSceneLoaded(Scene scene, LoadSceneMode mode) {
        if (scene.name == "MainMenu") {
            // Destrueix el player quan tornem al menú
            if (_playerInstance != null) {
                Destroy(_playerInstance);
                _playerInstance = null;
            }
            return;
        }
        GameObject spawnPoint = GameObject.Find("PlayerSpawnPoint");
        Vector3 pos = spawnPoint != null ? spawnPoint.transform.position : Vector3.zero;
        if (_playerInstance != null) {
            // El player ja existeix, només el reposiciona
            _playerInstance.transform.position = pos;
            SceneManager.MoveGameObjectToScene(_playerInstance, scene);
        }
        else {
            // Primera vegada, crea el player
            _playerInstance = Instantiate(playerPrefab, pos, Quaternion.identity);
            SceneManager.MoveGameObjectToScene(_playerInstance, scene);
        }
    }
}