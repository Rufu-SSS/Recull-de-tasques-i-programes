using UnityEngine;
using System.Collections.Generic;
public class PlayerInventory : MonoBehaviour
{
    private PlayerInventoryDisplay
    playerInventoryDisplay;
    private Dictionary<PickupUI.PickUpType, int> items
    = new Dictionary<PickupUI.PickUpType, int>();
    private Dictionary<PickupUI.PickUpType, PickupUI>
    icones = new Dictionary<PickupUI.PickUpType,
    PickupUI>();
    void Awake()
    {
        playerInventoryDisplay =
        GetComponent<PlayerInventoryDisplay>();
        playerInventoryDisplay.OnChangeInventory(items,
        icones);
    }
    public void Add(PickupUI pickup)
    {
        PickupUI.PickUpType type = pickup.type;
        int oldTotal = 0;
        if (items.TryGetValue(type, out oldTotal))
        {
            items[type] = oldTotal + 1;
        }
        else
        {
            items.Add(type, 1);
            icones.Add(type, pickup);
        }
        playerInventoryDisplay.OnChangeInventory(items,
        icones);
    }
    void OnTriggerEnter2D(Collider2D hit)
    {
        if (hit.CompareTag("Pickup"))
        {
            PickupUI item =
            hit.GetComponent<PickupUI>();
            Add(item);
            Destroy(hit.gameObject);
        }
    }
}