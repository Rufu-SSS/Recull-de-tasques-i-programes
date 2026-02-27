using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using UnityEngine;

public class SpawnManager : MonoBehaviour
{
    public GameObject[] enemyPrefabs;
    private float spawnRange = 9;
    public int enemyCount;
    public int waveNumber = 1;
    // Start is called before the first frame update
    void Start()
    {
        SpawnEnemyWave(3);
    }

    // Update is called once per frame

    void SpawnEnemyWave(int enemiesToSpawn)
    {
        for (int i = 0; i < enemiesToSpawn; i++)
        {
            SpawnRandomEnemy();
        }
    }

    void SpawnRandomEnemy()  // Mètode per generar un enemic aleatoriament.
    {
        int randomIndex = Random.Range(0, enemyPrefabs.Length);  // Genera un número aleatori entre 0 i la longitud de l'array enemyPrefabs per triar un enemic aleatori.
        GameObject enemyToSpawn = enemyPrefabs[randomIndex];  // Assigna a l'objecte enemyToSpawn el prefab d'enemic triat aleatòriament.
        Vector3 spawnPos = GenerateSpawnPosition();  // Crida el mètode GenerateSpawnPosition per obtenir una posició aleatòria per generar l'enemic.

        Instantiate(enemyToSpawn, spawnPos, Quaternion.identity);  // Genera una instància de l'enemic a la posició generada amb una rotació estàndard.
    }

    private Vector3 GenerateSpawnPosition()
    {
        float spawnPosX = Random.Range(-spawnRange, spawnRange);
        float spawnPosZ = Random.Range(-spawnRange, spawnRange);
        return new Vector3(spawnPosX, 0, spawnPosZ);
    }



    void Update()  // Mètode que s'executa cada frame.
    {
        enemyCount = FindObjectsOfType<Enemy>().Length;  // Comptabilitza quants enemics existeixen a l'escena en aquell moment.

        if (enemyCount == 0)  // Si no queden enemics a l'escena...
        {
            waveNumber++;  // Incrementa el número de la wave (passa a la següent wave).
            SpawnEnemyWave(waveNumber);  // Crida el mètode SpawnEnemyWave per generar nous enemics basant-se en el número de la wave.
        }
    }
}
