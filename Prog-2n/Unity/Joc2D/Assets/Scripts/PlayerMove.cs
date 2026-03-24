using UnityEngine;
using System;

public class PlayerMove : MonoBehaviour
{
    // ─── Events ───────────────────────────────────────────
    public static event Action<int> OnPickupCollected;

    // ─── Classes internes ─────────────────────────────────
    [System.Serializable]
    public class Estadistiques
    {
        public static Estadistiques PlayerInstance;
        public float vida;
        public int mana;
        public float Health
        {
            get => vida;
            set => vida = value;
        }
    }

    public class Jugador
    {
        public Estadistiques misStats;
    }

    // ─── Enum ─────────────────────────────────────────────
    public enum FACEDIRECTION { FACELEFT = -1, FACERIGHT = 1 }

    // ─── Variables públiques ──────────────────────────────
    public float speed = 10f;
    public float jumpForce = 12f;
    public bool isGrounded = true;
    public FACEDIRECTION Facing = FACEDIRECTION.FACERIGHT;
    public Estadistiques misStats = new Estadistiques() { vida = 100, mana = 50 };
    Animator animator;

    // ─── Variables privades ───────────────────────────────
    private int _pickupCount = 0;
    private Rigidbody2D _rb;
    private Vector2 _newVelocity;

    // ─── Awake ────────────────────────────────────────────
    private void Awake()
    {
        _rb = GetComponent<Rigidbody2D>();
        animator = GetComponent<Animator>();
        Estadistiques.PlayerInstance = misStats;
    }
    private void Start()
    {
        animator=GetComponent<Animator>();
    }

    // ─── Update ───────────────────────────────────────────
    void Update()
    {
        float xMove = Input.GetAxis("Horizontal");
        _newVelocity = new Vector2(xMove * speed, 0);

        animator.SetBool("isRunning", xMove != 0);

        if (Input.GetButtonDown("Jump") && isGrounded)
        {
            _rb.linearVelocity = new Vector2(_rb.linearVelocity.x, jumpForce);
            isGrounded = false;
            animator.SetBool("isJumping", !isGrounded);
        }
    }

    // ─── FixedUpdate ──────────────────────────────────────
    void FixedUpdate()
    {
        _rb.linearVelocity = new Vector2(_newVelocity.x, _rb.linearVelocity.y);

        if ((_newVelocity.x < 0f && Facing != FACEDIRECTION.FACELEFT) ||
            (_newVelocity.x > 0f && Facing != FACEDIRECTION.FACERIGHT))
        {
            FlipDirection();
        }
    }

    // ─── Col·lisions ──────────────────────────────────────
    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.CompareTag("Ground"))
        {
            isGrounded = true;
            animator.SetBool("isJumping", false);
        }
    }

    // ─── Mètodes privats ──────────────────────────────────
    private void FlipDirection()
    {
        Facing = (FACEDIRECTION)((int)Facing * -1);
        Vector3 localScale = transform.localScale;
        localScale.x *= -1f;
        transform.localScale = localScale;
    }

    // ─── Mètodes estàtics ─────────────────────────────────
    public static void NotifyPickupCollected(int total)
    {
        OnPickupCollected?.Invoke(total);
    }
    void OnTriggerEnter2D(Collider2D other)
    {
        if (other.CompareTag("Player"))
        {
            other.GetComponent<PlayerHealth>().TakeDamage(1);
        }
    }

}