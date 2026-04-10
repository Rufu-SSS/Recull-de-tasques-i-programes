using System.Collections;
using UnityEngine;

public class Target : MonoBehaviour
{
    private GameManager _gameManager;
    private Rigidbody _targetRb;

    [Header("Moviment")]
    private float _minSpeed = 12f;
    private float _maxSpeed = 16f;
    private float _maxTorque = 10f;
    private float _xRange = 4f;
    private float _ySpawnPos = -2f;
    private float _minYPosition = -3f;

    [Header("Valors")]
    public int pointValue;
    public int livesValue;

    [Header("Efectes")]
    public ParticleSystem explosionParticle;

    void Start()
    {
        _targetRb = GetComponent<Rigidbody>();
        _gameManager = GameObject.Find("GameManager")?.GetComponent<GameManager>();
        if (_gameManager == null)
        {
            Debug.LogError("No es troba el GameManager!");
            return;
        }
        transform.position = RandomSpawnPos();
        _targetRb.AddForce(RandomForce(), ForceMode.Impulse);
        _targetRb.AddTorque(RandomTorque(), RandomTorque(), RandomTorque(), ForceMode.Impulse);
    }

    void Update()
    {
        if (transform.position.y < _minYPosition)
        {
            // FIX: comprova el tag del propi objecte, no del GameManager
            if (_gameManager != null && _gameManager.isGameActive && !gameObject.CompareTag("Bad"))
            {
                _gameManager.UpdateLives(livesValue);
            }
            Destroy(gameObject);
        }
    }

    private void OnMouseDown()
    {
        if (_gameManager == null || !_gameManager.isGameActive) return;
        Destroy(gameObject);
        _gameManager.UpdateScore(pointValue);
        _gameManager.UpdateLives(livesValue);
        if (explosionParticle != null)
            Instantiate(explosionParticle, transform.position, explosionParticle.transform.rotation);
    }

    private void OnTriggerEnter(Collider other)
    {
        Destroy(gameObject);
    }

    private Vector3 RandomForce()
    {
        return Vector3.up * Random.Range(_minSpeed, _maxSpeed);
    }

    private float RandomTorque()
    {
        return Random.Range(-_maxTorque, _maxTorque);
    }

    private Vector3 RandomSpawnPos()
    {
        // FIX: afegit el 0f per la coordenada Z
        return new Vector3(Random.Range(-_xRange, _xRange), _ySpawnPos, 0f);
    }
}