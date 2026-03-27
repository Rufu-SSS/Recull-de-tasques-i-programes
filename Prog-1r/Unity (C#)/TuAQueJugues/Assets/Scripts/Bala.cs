using UnityEngine;

public class Bala : MonoBehaviour
{
    public float Velocitat = 10f;
    public int Dany = 10;
    public bool EsDelJugador = true;
    private Collider2D _colliderNauPare;

    // Qui dispara passa el seu collider per ignorar-lo
    public void Inicialitzar(bool esDelJugador, Collider2D colliderPare) {
        EsDelJugador = esDelJugador;
        _colliderNauPare = colliderPare;
        // Ignora f�sicament la col�lisi� amb qui ha disparat
        if (_colliderNauPare != null)
            Physics2D.IgnoreCollision(GetComponent<Collider2D>(), _colliderNauPare);
    }
    void Update() {
        Vector3 direccio = EsDelJugador ? Vector3.up : Vector3.down;
        transform.Translate(direccio * Velocitat * Time.deltaTime);
    }

    private void OnTriggerEnter2D(Collider2D other) {
        if (EsDelJugador) {
            NauEnemiga enemic = other.GetComponent<NauEnemiga>();
            if (enemic != null) {
                enemic.RebreDany(Dany);
                Destroy(gameObject);
            }
        }
        else {
            NauJugador jugador = other.GetComponent<NauJugador>();
            if (jugador != null) {
                jugador.RebreDany(Dany);
                Destroy(gameObject);
            }
        }
    }
    void OnBecameInvisible() { Destroy(gameObject); }
}