
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.XR;

public class PlayerController : MonoBehaviour
{
    // Start is called before the first frame update
    private Rigidbody playerRb;
    public float speed = 5.0f;
    public float jumpForce = 10.0f;
    private bool tocaTerra = true;

    private GameObject focalPoint;
    public bool hasPowerup;
    private float powerupStrength = 15.0f;

    void Start()
    {
        playerRb = GetComponent<Rigidbody>();
        focalPoint = GameObject.Find("Focal Point");
    }

    // Update is called once per frame
    void Update()
    {
        float moveX = Input.GetAxis("Horizontal");
        float moveZ = Input.GetAxis("Vertical");
        playerRb.linearVelocity=new Vector3(moveX * speed, playerRb.linearVelocity.y, moveZ * speed);

        if(Input.GetKeyDown(KeyCode.Space) && tocaTerra)
        {
            playerRb.AddForce(Vector3.up * jumpForce, ForceMode.Impulse);
            tocaTerra = false;
        }
    }

    private void OnTriggerEnter(Collider other)
    {
        if (other.CompareTag("Powerup"))
        {
            hasPowerup = true;
            Destroy(other.gameObject);
            StartCoroutine(PowerupCountdownRoutine());
        } 
    }
    IEnumerator PowerupCountdownRoutine()
    {
        yield return new WaitForSeconds(7);
        hasPowerup = false;
    }
   
    private void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Ground"))
        {
            tocaTerra = true;
        }
        
        if(collision.gameObject.CompareTag("Enemy")&& hasPowerup)
        {
            Rigidbody enemyRigidbody = collision.gameObject.GetComponent<Rigidbody>();
            Vector3 awayFromPlayer = (collision.gameObject.transform.position - transform.position);
            enemyRigidbody.AddForce(awayFromPlayer * powerupStrength, ForceMode.Impulse);

        }
        if (collision.gameObject.CompareTag("Enemy"))
        {
            float playerY = transform.position.y;
            float enemyY = collision.gameObject.transform.position.y;
            if (playerY > enemyY + 0.2f)
            {
                Destroy(collision.gameObject);
                Debug.Log("Enemic mort");
            }
        }

    }








}
