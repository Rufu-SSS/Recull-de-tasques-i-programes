using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UIElements;

public class MainMenuController : MonoBehaviour {
    void Start() {
        var root = GetComponent<UIDocument>().rootVisualElement;

        root.Q<Button>("PlayButton").clicked += () => SceneManager.LoadScene("Level1");
        root.Q<Button>("ExitButton").clicked += () => ExitGame();
    }
    private void ExitGame() {
#if UNITY_EDITOR
        UnityEditor.EditorApplication.isPlaying = false;  // Para el Play mode a l'editor
#else
    Application.Quit();  // Tanca l'exe en build final
#endif
    }
}
