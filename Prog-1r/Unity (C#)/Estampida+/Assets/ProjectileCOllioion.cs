using UnityEngine;

public class ProjectileCollision : MonoBehaviour
{
    void OnTriggerEnter(Collider other)
    {
        if (other.CompareTag("Animal"))
        {
            other.GetComponent<AnimalHunger>().FeedAnimal();
            Destroy(gameObject);
        }
    }
}