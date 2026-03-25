using System.Collections;
using UnityEngine;
using TMPro;
public class DialogueManager : MonoBehaviour {
    public static DialogueManager Instance;
    [Header("UI References")]
    public GameObject dialoguePanel;
    public TextMeshProUGUI nameText;
    public TextMeshProUGUI dialogueText;
    [Header("Typewriter")]
    public float typingSpeed = 0.04f;
    private string[] currentLines;
    private int currentIndex;
    private bool isTyping = false;
    private bool dialogueActive = false;
    void Awake() {
        Instance = this;
        dialoguePanel.SetActive(true);
    }
    public void StartDialogue(DialogueData data) {
        dialogueActive = true;
        currentLines = data.lines;
        currentIndex = 0;
        nameText.text = data.npcName;
        dialoguePanel.SetActive(true);
        StartCoroutine(TypeLine(currentLines[currentIndex]));
    }
    void Update() {
        if (!dialogueActive) return;
        if (Input.GetKeyDown(KeyCode.E)) {
            if (isTyping) {
                // Si encara escriu, mostra el text sencer immediatament
                StopAllCoroutines();
                dialogueText.text = currentLines[currentIndex];
                isTyping = false;
            }
            else {
                NextLine();
            }
        }
    }
    void NextLine() {
        currentIndex++;
        if (currentIndex < currentLines.Length) {
            StartCoroutine(TypeLine(currentLines[currentIndex]));
        }
        else {
            EndDialogue();
        }
    }
    IEnumerator TypeLine(string line) {
        isTyping = true;
        dialogueText.text = "";
        foreach (char c in line) {
            dialogueText.text += c;
            yield return new WaitForSeconds(typingSpeed);
        }
        isTyping = false;
    }
    void EndDialogue() {
        dialogueActive = false;
        dialoguePanel.SetActive(false);
    }
    public bool IsActive() => dialogueActive;
}