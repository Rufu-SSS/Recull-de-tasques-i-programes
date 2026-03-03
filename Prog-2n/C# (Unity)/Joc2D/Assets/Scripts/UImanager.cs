using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UIElements;

public class UIManager : MonoBehaviour
{
    private UIDocument _document;
    private Label _pickupCountLabel;
    private Label _inventoryLabel;

    void OnEnable()
    {
        _document = GetComponent<UIDocument>();
        var root = _document.rootVisualElement;

        // Connecta amb els elements del UXML pel seu 'name'
        _pickupCountLabel = root.Q<Label>("pickup-count");
        _inventoryLabel = root.Q<Label>("inventory-label");

        // Subscriu-te als events del joc
        PlayerMove.OnPickupCollected += UpdatePickupUI;
        PlayerInventory.OnInventoryChanged += UpdateInventoryUI;
    }

    void OnDisable()
    {
        PlayerMove.OnPickupCollected -= UpdatePickupUI;
        PlayerInventory.OnInventoryChanged -= UpdateInventoryUI;
    }

    void UpdatePickupUI(int total)
    {
        _pickupCountLabel.text = $"Pickups: {total}";
    }

    void UpdateInventoryUI(List<string> items)
    {
        _inventoryLabel.text = string.Join(", ", items);
    }
}