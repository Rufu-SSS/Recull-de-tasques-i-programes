using UnityEngine;

public class PlayerMove : MonoBehaviour
{
    public float speed = 10;
    private Rigidbody2D rigidbody2D;
    private Vector2 newVelocity;

    public enum FACEDIRECTION { FACELEFT = -1, FACERIGHT = 1 };
    public FACEDIRECTION Facing = FACEDIRECTION.FACERIGHT;

    private void FlipDirection()
    {
        Facing = (FACEDIRECTION)((int)Facing * -1);
        Vector3 localScale = transform.localScale;
        localScale.x *= -1f;
        transform.localScale = localScale;
    }

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

    void FixedUpdate()
    {
        rigidbody2D.linearVelocity = newVelocity;

        if ((newVelocity.x < 0f && Facing != FACEDIRECTION.FACELEFT) ||
            (newVelocity.x > 0f && Facing != FACEDIRECTION.FACERIGHT))
        {
            FlipDirection();
        }
    }
}