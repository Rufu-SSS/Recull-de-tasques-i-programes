using TMPro;
using UnityEngine;

public class FinalPartida : MonoBehaviour
{
    public JugadorManager jugadorManager;
    public TMP_InputField inputNom;
    public GameManager gameManager; // ← assigna-ho directament a l'Inspector

    public void Finalitzar()
    {
        if (string.IsNullOrEmpty(inputNom.text))
        {
            Debug.LogWarning("Escriu un nom!");
            return;
        }

        jugadorManager.AfegirJugador(inputNom.text, gameManager.Score);
        jugadorManager.MostrarTop3();
    }
}