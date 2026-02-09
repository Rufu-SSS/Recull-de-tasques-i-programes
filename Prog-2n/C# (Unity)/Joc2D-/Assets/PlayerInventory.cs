using UnityEngine;

public class PlayerInventory : MonoBehaviour
{
    private PlayerInventoryDisplay playerInventoryDisplay;
    private bool carryingStar = false;
    private void Awake()
    {
        playerInventoryDisplay = GetComponent<PlayerInventoryDisplay>();
    }

    private void Start()
    {
        playerInventoryDisplay.OnChangeCarryingStar(carryingStar);
    }

    private void OnTriggerEnter2D(Collider2D hit)
    {
        if (hit.CompareTag("Estel"))
        {
            carryingStar = true;
            playerInventoryDisplay. OnCarryingStar(carryingStar);
            Destroy(hit.gameObject);
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
