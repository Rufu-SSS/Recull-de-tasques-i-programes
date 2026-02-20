using UnityEngine;
using UnityEngine.UI;

public class AnimalHunger : MonoBehaviour
{
    public Slider hungerBar;
    public float maxHunger = 5f;
    private float currentHunger;

    void Start()
    {
        currentHunger = maxHunger;
        hungerBar.maxValue = maxHunger;
        hungerBar.value = currentHunger;
    }

    void Update()
    {
        currentHunger -= Time.deltaTime;
        hungerBar.value = currentHunger;

        if (currentHunger <= 0)
        {
            GameManager.instance.LoseLife();
            Destroy(gameObject);
        }
    }

    public void FeedAnimal()
    {
        currentHunger = maxHunger;
        hungerBar.value = currentHunger;
        GameManager.instance.AddScore(1);
    }
}