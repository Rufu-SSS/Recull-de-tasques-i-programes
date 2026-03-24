using UnityEngine;
using UnityEngine.SceneManagement;

public class KillZone : MonoBehaviour
{
public float Damage = 100f;
    void OnTriggerStay2D(Collider2D other)
    {
        if (!other.CompareTag("Player")) return;
        if (PlayerHealth.Instance != null)
            PlayerHealth.Instance.TakeDamage(Mathf.CeilToInt(Damage * Time.deltaTime));
    }
}
