using UnityEngine;

public class PlayerMove : MonoBehaviour
{
    public float speed = 10;
    private Rigidbody2D rigidbody2D;
    private Vector2 newVelocity;
    private void Awake()
    {
        rigidbody2D = GetComponent<Rigidbody2D>();
    }
    void Update()
    {
        float xMove = Input.GetAxis("Horizontal");
        float yMove = Input.GetAxis("Vertical");
        float xSpeed = xMove * speed;
        float ySpeed = yMove * speed;
        newVelocity = new Vector2(xSpeed, ySpeed);
    }
    private void FixedUpdate()
    {
        rigidbody2D.linearVelocity = newVelocity;
    }

}
