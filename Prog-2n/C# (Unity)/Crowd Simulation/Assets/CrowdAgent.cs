using UnityEngine;
using UnityEngine.AI;

public class CrowdAgent : MonoBehaviour
{
    public Transform target;
    private NavMeshAgent agent;
 
    void Start()
    {
        agent = GetComponent<NavMeshAgent>();
    }
    void Update()
    {
        if (target != null)
            agent.SetDestination(target.position);
    }
}
