using UnityEngine;

public class NPCInteraction : MonoBehaviour
{
    public DialogueData dialogueData;
    public GameObject interactPrompt; // el text [E] Parlar

    private bool playerNearby = false;

    void Update()
    {
        if (playerNearby && Input.GetKeyDown(KeyCode.E))
        {
            if (!DialogueManager.Instance.IsActive())
                DialogueManager.Instance.StartDialogue(dialogueData);
        }
    }

    void OnTriggerEnter2D(Collider2D other)
    {
        if (other.CompareTag("Player"))
        {
            playerNearby = true;
            if (interactPrompt) interactPrompt.SetActive(true);
        }
    }

    void OnTriggerExit2D(Collider2D other)
    {
        if (other.CompareTag("Player"))
        {
            playerNearby = false;
            if (interactPrompt) interactPrompt.SetActive(false);
        }
    }
}