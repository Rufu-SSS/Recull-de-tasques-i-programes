using UnityEngine;

public class AnimalMovement : MonoBehaviour
{
    public float speed = 5;

    void Update()
    {
        transform.Translate(Vector3.forward * speed * Time.deltaTime);
    }
}