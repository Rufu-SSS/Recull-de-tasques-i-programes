using UnityEngine;

public class FoodMovement : MonoBehaviour
{
    public float speed = 15f;
    private float destroyZ = 30f;

    void Update()
    {
        transform.Translate(Vector3.forward * speed * Time.deltaTime);

        if (transform.position.z > destroyZ)
        {
            Destroy(gameObject);
        }
    }
}