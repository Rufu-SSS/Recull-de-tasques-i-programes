using TMPro;
using UnityEngine;
using UnityEngine.UI;
public class FinalPartida : MonoBehaviour
{
    public JugadorManager jugadorManager;
    public TMP_InputField InputNom;
    public int puntuacioJugador = 72;

    public void Finalitzar()
    {
        jugadorManager.AfegirJugador(InputNom.text, puntuacioJugador);
        jugadorManager.MostrarTop3();
    }
}
