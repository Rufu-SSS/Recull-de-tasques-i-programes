using UnityEngine;

public class LimitPantalla : MonoBehaviour {
    private float _limitInferior = -6f;
    private NauJugador _jugador;
    void Start() {
        _jugador = FindObjectOfType<NauJugador>();
    }
    void Update() {
        if (transform.position.y < _limitInferior) {
            _jugador?.RebreDany(10);
            Destroy(gameObject);
        }
    }
}