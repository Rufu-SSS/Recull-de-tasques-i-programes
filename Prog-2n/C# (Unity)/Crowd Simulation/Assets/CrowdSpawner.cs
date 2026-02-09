using UnityEngine;

public class CrowdSpawner : MonoBehaviour
{
    public GameObject agentPrefab;
    public Transform targetA;
    public Transform targetB;

    public int agentsPerGroup = 5;
    public Vector3 spawnAreaA;
    public Vector3 spawnAreaB;

    void Start()
    {
        SpawnGroup(agentsPerGroup, spawnAreaA, targetA);
        SpawnGroup(agentsPerGroup, spawnAreaB, targetB);
    }

    void SpawnGroup(int count, Vector3 center, Transform target)
    {
        for (int i = 0; i < count; i++)
        {
            Vector3 offset = new Vector3(
                Random.Range(-2f, 2f),
                0,
                Random.Range(-2f, 2f)
            );

            GameObject agent = Instantiate(
                agentPrefab,
                center + offset,
                Quaternion.identity
            );

            agent.GetComponent<CrowdAgent>().target = target;
        }
    }
}
