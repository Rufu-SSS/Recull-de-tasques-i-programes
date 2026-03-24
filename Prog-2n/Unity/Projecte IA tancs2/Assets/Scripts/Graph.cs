using UnityEngine;

public class Graph : MonoBehaviour
{
    public GameObject[] Ruta;
    private Vector3 objectiu;
    private float velocitat = 4.0f;
    private float DistanciaLimit = 0.5f;
    private float VelRotacio = 4f;
    private int IndexNode = 0;

    private void Start()
    {
        
    }

    void Update()
    {
        if (Ruta.Length == 0)
            return;

        objectiu = new Vector3(Ruta[IndexNode].transform.position.x, this.transform.position.y, Ruta[IndexNode].transform.position.z);
        Vector3 direction = objectiu - this.transform.position;
        float distancia = direction.magnitude;

        if (distancia <= DistanciaLimit)
        {
            IndexNode++;

            if (IndexNode >= Ruta.Length)
            {
                IndexNode = 0;
            }
        }
        else
        {
            Vector3 moveDirection = direction.normalized;
            this.transform.position += moveDirection * velocitat * Time.deltaTime;

            Quaternion targetRotation = Quaternion.LookRotation(direction);
            transform.rotation = Quaternion.Slerp(transform.rotation, targetRotation, VelRotacio * Time.deltaTime);
        }
    }
}

/*
if (direction.magnitude > DistanciaLimit)
{
    // Interpolates rotation between the rotations "from" and "to"
    this.transform.rotation = Quaternion.Slerp(this.transform.rotation, Quaternion.
    LookRotation(direction), Time.deltaTime * VelRotacio);
    this.transform.Translate(0, 0, Velocitat * Time.deltaTime);
}
else
{
    if (IndexNode < ruta.Length - 1)
    {
        IndexNode++;
    }
    else
    {
        IndexNode = 0;
    }
}
*/