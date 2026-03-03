using UnityEngine;
using System;

public class PlayerMove : MonoBehaviour
{
    public static event Action<int> OnPickupCollected; 
    private int _pickupCount = 0;                      

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
        newVelocity = new Vector2(xMove * speed, yMove * speed);
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

    // Cridat des de PlayerInventory quan es recull un pickup
    public static void NotifyPickupCollected(int total)
    {
        OnPickupCollected?.Invoke(total);
    }
}