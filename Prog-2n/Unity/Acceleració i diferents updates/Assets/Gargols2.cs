using UnityEngine;

public class Cargols2 : MonoBehaviour
{
    public Rigidbody rb;
    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        rb = GetComponent<Rigidbody>();
    }

    // Update is called once per frame
    void FixedUpdate()
    {
        rb.AddForce(10.0f * Vector3.forward);
    }
}
