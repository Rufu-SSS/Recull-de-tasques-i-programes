using UnityEngine;

public class Player : MonoBehaviour
{
    public float jumpForce = 10f;
    public Rigidbody2D rb;
    public bool isGrounded;
    private GameManager gameManager;

    void Start()
    {
        rb = GetComponent<Rigidbody2D>();
        gameManager = FindObjectOfType<GameManager>();

        if (gameManager == null)
        {
            Debug.LogError("GameManager no trobat!");
        }
    }

    void Update()
    {
        if (Input.GetMouseButtonDown(0) && isGrounded)
        {
            rb.linearVelocity = Vector2.up * jumpForce;
        }
    }

    public void Die()
    {
        Debug.Log("Player.Die() cridat!"); //← DEPURACIÓ
        if (gameManager != null)
        {
            gameManager.GameOver();
        }
    }

    //... resta del codi igual
}