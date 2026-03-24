using UnityEngine;
using System.Collections.Generic;
using System.Collections;
using UnityEngine.AI;
public class Cop : MonoBehaviour
{
    NavMeshAgent cop;
    public GameObject targetC;

    void Start()
    {
        cop = this.GetComponent<NavMeshAgent>();
    }

    void Pursuit(Transform targetD)
    {
        if (targetD == null) return;

        NavMeshAgent targetAgent = targetD.GetComponent<NavMeshAgent>();
        if (targetAgent == null)
        {
            cop.SetDestination(targetD.position);
            return;
        }
                float distance = Vector3.Distance(cop.transform.position, targetD.position);
        float predict = distance / cop.speed;
        Vector3 futurePosition = targetD.position + targetAgent.velocity * predict;
        cop.SetDestination(futurePosition);
        
    }   

    // Update is called once per frame
    void Update()
    {
        Pursuit(targetC.transform);
    }
}
