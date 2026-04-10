using UnityEngine;

public class DestroyAfterDelay : MonoBehaviour
{
    public float delay = 2f; // configurable des de l'Inspector

    void Start()
    {
        Destroy(gameObject, delay);
    }
}