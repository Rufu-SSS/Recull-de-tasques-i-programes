using UnityEngine;

public class SpawnManager : MonoBehaviour
{
    public GameObject[] animalPrefabs;

    private float spawnRange = 20;
    private float startDelay = 2;
    private float spawnInterval = 1.5f;

    void Start()
    {
        InvokeRepeating("SpawnRandomAnimal", startDelay, spawnInterval);
    }

    void SpawnRandomAnimal()
    {
        int animalIndex = Random.Range(0, animalPrefabs.Length);
        int side = Random.Range(0, 2);

        Vector3 spawnPos;

        if (side == 0) // esquerra
        {
            spawnPos = new Vector3(-spawnRange, 0, Random.Range(-spawnRange, spawnRange));
        }
        else // dreta
        {
            spawnPos = new Vector3(spawnRange, 0, Random.Range(-spawnRange, spawnRange));
        }

        Instantiate(animalPrefabs[animalIndex], spawnPos,
            animalPrefabs[animalIndex].transform.rotation);
    }
}