using UnityEngine;

public class PickupSpawner : MonoBehaviour
{
    [Header("Prefabs dels Pickups")]
    public GameObject[] pickupPrefabs;  // Arrossega els prefabs aquí a l'Inspector

    [Header("Configuració")]
    public float spawnInterval = 5f;    // Cada quants segons apareix un nou pickup
    public int maxPickups = 10;         // Màxim de pickups a l'escena alhora

    [Header("Zona d'aparició")]
    public Vector2 spawnAreaMin = new Vector2(-8f, -4f);  // Cantonada inferior esquerra
    public Vector2 spawnAreaMax = new Vector2(8f, 4f);    // Cantonada superior dreta

    private float _timer;

    void Update()
    {
        _timer += Time.deltaTime;

        if (_timer >= spawnInterval)
        {
            _timer = 0f;
            TrySpawnPickup();
        }
    }

    void TrySpawnPickup()
    {
        // Compta quants pickups hi ha ara mateix
        int current = GameObject.FindGameObjectsWithTag("Pickup").Length;

        if (current >= maxPickups) return;  // No en genera més si ja hi ha el màxim

        // Escull un prefab a l'atzar
        GameObject prefab = pickupPrefabs[Random.Range(0, pickupPrefabs.Length)];

        // Posició aleatòria dins la zona
        Vector2 pos = new Vector2(
            Random.Range(spawnAreaMin.x, spawnAreaMax.x),
            Random.Range(spawnAreaMin.y, spawnAreaMax.y)
        );

        Instantiate(prefab, pos, Quaternion.identity);
    }
}