using System;
using UnityEngine;

public class PlayerHealth : MonoBehaviour
{
    public static PlayerHealth Instance;

    public static event Action OnPlayerDied;

    public int maxHealth = 4;
    public int currentHealth;

    void Awake()
    {
        Instance = this;
    }

    void Start()
    {
        currentHealth = maxHealth;
        HealthUI.Instance.UpdateHearts(currentHealth);
    }

    public void TakeDamage(int amount)
    {
        currentHealth -= amount;
        currentHealth = Mathf.Clamp(currentHealth, 0, maxHealth);
        HealthUI.Instance.UpdateHearts(currentHealth);

        if (currentHealth <= 0)
            Die();
    }

    public void Heal(int amount)
    {
        currentHealth += amount;
        currentHealth = Mathf.Clamp(currentHealth, 0, maxHealth);
        HealthUI.Instance.UpdateHearts(currentHealth);
    }

    void Die()
    {
        Debug.Log("Game Over!");
        OnPlayerDied?.Invoke();
    }
}