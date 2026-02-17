using UnityEngine;
using UnityEngine.UI;
public class PickupUI : MonoBehaviour
{
    public enum PickUpType
    {
        Star, Key,
        Heart
    }
    public PickUpType type;
    public Image iconColor;
}