using UnityEngine;

public class JumpPad : MonoBehaviour
{
    public float force = 15f;

    void OnTriggerEnter2D(Collider2D col)
    {
        if (col.CompareTag("Player"))
        {
            Player player = col.GetComponent<Player>();
            if (player != null) // ← MILLORA: comprovació de seguretat
            {
                player.Boost(force); // ← MILLORA: usa el mètode nou
            }
        }
    }
}