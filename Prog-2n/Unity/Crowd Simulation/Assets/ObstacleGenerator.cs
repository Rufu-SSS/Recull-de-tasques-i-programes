using UnityEngine;
using UnityEngine.AI;

public class ObstacleGenerator : MonoBehaviour
{
    public GameObject obstaclePrefab;
    public int obstacleCount = 10;
    public Vector3 areaSize = new Vector3(20, 0, 20);

    void Start()
    {
        for (int i = 0; i < obstacleCount; i++)
        {
            Vector3 pos = new Vector3(
                Random.Range(-areaSize.x / 2, areaSize.x / 2),
                0,
                Random.Range(-areaSize.z / 2, areaSize.z / 2)
            );

            Instantiate(obstaclePrefab, pos, Quaternion.identity);
        }
    }
}
