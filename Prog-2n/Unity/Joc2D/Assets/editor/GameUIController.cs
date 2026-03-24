using UnityEngine;
using UnityEngine.UIElements;

public class GameUIController : MonoBehaviour
{
    public static GameUIController Instance;
    private Label inventoryLabel;

    void Awake()
    {
        Instance = this;
    }

    void Start()
    {
        var root = GetComponent<UIDocument>().rootVisualElement;
        inventoryLabel = root.Q<Label>("InventoryLabel");
        UpdateInventory(0);
    }

    public void UpdateInventory(int value)
    {
        inventoryLabel.text = "Inventory: " + value;
    }
}