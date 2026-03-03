using System.Collections.Generic;
using TMPro;
using UnityEngine;
[RequireComponent(typeof(PlayerInventory))]
public class PlayerInventoryDisplay : MonoBehaviour
{
    public TextMeshProUGUI inventoryText;
    public void OnChangeInventory(Dictionary<PickupUI.PickUpType, int> inventory,
    Dictionary<PickupUI.PickUpType, PickupUI> SetIcones)
    {
        inventoryText.text = "";
        string newInventoryText = "carrying: ";
        foreach (var item in inventory)
        {
            int itemTotal = item.Value;
            string description = item.Key.ToString();
            newInventoryText += " [ " + description + " " + itemTotal + " ]";
            PickupUI IconSeleccionat = null;
            if (SetIcones.TryGetValue(item.Key, out IconSeleccionat))
            {
                float newWidth = 100 * itemTotal;
                IconSeleccionat.iconColor.rectTransform.SetSizeWithCurrentAnchors(
                RectTransform.Axis.Horizontal, newWidth);
            }
        }
        int numItems = inventory.Count;
        if (numItems < 1) newInventoryText = "(empty inventory)";
        inventoryText.text = newInventoryText;
    }
}