using UnityEngine;
using UnityEngine.InputSystem;

public class PlayerController : MonoBehaviour
{
    private Rigidbody rb;
    private float movementX;
    private float movementY;

    public float speed = 10;
    private float xRange = 15;
    private float zRange = 10;

    public GameObject projectilePrefab;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
    }

    void OnMove(InputValue movementValue)
    {
        Vector2 movementVector = movementValue.Get<Vector2>();
        movementX = movementVector.x;
        movementY = movementVector.y;
    }
    void OnAttack(InputValue fireValue)
    {
        Instantiate(projectilePrefab, transform.position, projectilePrefab.transform.rotation);
    }

    void FixedUpdate()
    {
        Vector3 movement = new Vector3(movementX, 0.0f, movementY);
        rb.linearVelocity = movement * speed;

        // Límit horitzontal
        if (transform.position.x < -xRange)
            transform.position = new Vector3(-xRange, transform.position.y, transform.position.z);

        if (transform.position.x > xRange)
            transform.position = new Vector3(xRange, transform.position.y, transform.position.z);

        // Límit vertical
        if (transform.position.z < -zRange)
            transform.position = new Vector3(transform.position.x, transform.position.y, -zRange);

        if (transform.position.z > zRange)
            transform.position = new Vector3(transform.position.x, transform.position.y, zRange);
    }

    
}