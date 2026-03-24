using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : MonoBehaviour
{
    private List<Node> path;
    private int index;
    private float speed;
    private float rotSpeed;
    private float minDist;
    private Color trailColor;

    public void Initialize(List<Node> pathToFollow, float moveSpeed, float rotationSpeed, float minDistance)
    {
        path = pathToFollow;
        speed = moveSpeed;
        rotSpeed = rotationSpeed;
        minDist = minDistance;
        index = 0;

        // Guardem el color de l'esfera
        trailColor = GetComponent<Renderer>().material.color;
    }

    void Update()
    {
        if (path == null || index >= path.Count) return;

        Vector3 target = path[index].Waypoint.transform.position;
        target.y = transform.position.y;

        Vector3 dir = target - transform.position;

        if (dir.magnitude > minDist)
        {
            Quaternion rot = Quaternion.LookRotation(dir);
            transform.rotation = Quaternion.Slerp(transform.rotation, rot, rotSpeed * Time.deltaTime);
            transform.position += dir.normalized * speed * Time.deltaTime;
        }
        else
        {
            // Pintar la cel·la sota l'esfera
            Renderer r = path[index].Waypoint.GetComponent<Renderer>();
            if (r != null)
            {
                r.material.color = trailColor;
            }

            index++;
        }
    }
}
