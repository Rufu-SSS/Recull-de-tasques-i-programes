using UnityEngine;

public class Obstacle : MonoBehaviour
{
    void OnTriggerEnter2D(Collider2D col)
    {
        Debug.Log("Alguna cosa ha entrat: " + col.gameObject.name); //← DEPURACIÓ

        if (col.CompareTag("Player"))
        {
            Debug.Log("Player detectat!"); //← DEPURACIÓ

            Player player = col.GetComponent<Player>();
            if (player != null)
            {
                player.Die();
            }
            else
            {
                Debug.LogError("No s'ha trobat el component Player!");
            }
        }
    }
}