using UnityEngine;
using System;
using System.Collections.Generic;

public class PlayerInventory : MonoBehaviour
{
    public static event Action<List<string>> OnInventoryChanged; // <-- AFEGIR

    private PlayerInventoryDisplay playerInventoryDisplay;
    private Dictionary<PickupUI.PickUpType, int> items = new();
    private Dictionary<PickupUI.PickUpType, PickupUI> icones = new();

    void Awake()
    {

        if (FindObjectsByType<PlayerInventory>(FindObjectsSortMode.None).Length > 1)
        {
            Destroy(gameObject);
            return;
        }

        // Sobreviu entre escenes
        DontDestroyOnLoad(gameObject);

        playerInventoryDisplay = GetComponent<PlayerInventoryDisplay>();
        playerInventoryDisplay.OnChangeInventory(items, icones);
    }

    public void Add(PickupUI pickup)
    {
        PickupUI.PickUpType type = pickup.type;
        if (items.TryGetValue(type, out int oldTotal))
            items[type] = oldTotal + 1;
        else
        {
            items.Add(type, 1);
            icones.Add(type, pickup);
        }

        playerInventoryDisplay.OnChangeInventory(items, icones);

        // Notifica la UI                              // <-- AFEGIR
        NotifyUI();
    }

    void OnTriggerEnter2D(Collider2D hit)
    {
        if (hit.CompareTag("Pickup"))
        {
            PickupUI item = hit.GetComponent<PickupUI>();
            Add(item);
            Destroy(hit.gameObject);

            // Notifica el compte total a PlayerMove   // <-- AFEGIR
            PlayerMove.NotifyPickupCollected(items.Count);
        }
    }

    private void NotifyUI()                            // <-- AFEGIR
    {
        var names = new List<string>();
        foreach (var item in items)
            names.Add($"{item.Key}: {item.Value}");
        OnInventoryChanged?.Invoke(names);
    }
}