using UnityEngine;
using UnityEngine.UI;

public class HealthUI : MonoBehaviour
{
    public static HealthUI Instance;

    public Image[] hearts; // arrossega els 4 cors aquí
    public Color fullColor = Color.red;
    public Color emptyColor = new Color(1, 1, 1, 0.2f); // blanc transparent

    void Awake()
    {
        Instance = this;
    }

    public void UpdateHearts(int currentHealth)
    {
        for (int i = 0; i < hearts.Length; i++)
        {
            hearts[i].color = (i < currentHealth) ? fullColor : emptyColor;
        }
    }
}