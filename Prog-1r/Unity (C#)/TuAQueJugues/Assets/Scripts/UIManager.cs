using UnityEngine;
using TMPro;

public class UIManager : MonoBehaviour {
    public TextMeshProUGUI TextPuntuacio;
    public TextMeshProUGUI TextVida;
    private NauJugador _jugador;
    void Start() {
        _jugador = FindObjectOfType<NauJugador>();
        ActualitzarUI();
    }
    void Update() {
        ActualitzarUI();
    }
    void ActualitzarUI() {
        if (_jugador == null) return;
        TextPuntuacio.text = "Puntuaci�: " + _jugador.Puntuacio;
        TextVida.text = "Vida: " + _jugador.Vida;
    }
}