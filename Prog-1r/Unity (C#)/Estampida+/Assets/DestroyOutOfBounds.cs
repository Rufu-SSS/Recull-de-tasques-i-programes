using UnityEngine;

public class DestroyOutOfBounds : MonoBehaviour
{
    private float bound = 25;

    void Update()
    {
        if (Mathf.Abs(transform.position.x) > bound || Mathf.Abs(transform.position.z) > bound)
        {
            if (CompareTag("Animal"))
            {
                GameManager.instance.LoseLife();
            }

            Destroy(gameObject);
        }
    }
}