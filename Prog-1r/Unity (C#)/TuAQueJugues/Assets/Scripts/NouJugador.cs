using UnityEngine;

// CONCEPTE 3: Herència — NauJugador HERETA de Nau
// CONCEPTE 5: Overriding — reimplementa RebreDany i Morir
public class NauJugador : Nau {
    public float Velocitat = 5f;
    public int Puntuacio = 0;
    public GameObject PrefabProjectil;
    protected override void Start() {
        NomNau = "Àguila-1";
        VidaMaxima = 100;
        Arma = TipusArma.Laser;  // CONCEPTE 1: enumeració
        base.Start(); // crida el Start() de Nau
    }
    void Update() {
        MoureLateral();
        GestionarDispar();
    }
    private void MoureLateral() {
        float h = Input.GetAxis("Horizontal");
        transform.Translate(Vector3.right * h * Velocitat * Time.deltaTime);
        float v = Input.GetAxis("Vertical");
        transform.Translate(Vector3.up * v * Velocitat * Time.deltaTime);
    }
    private void GestionarDispar() {
        // CONCEPTE 2: Sobrecàrrega — escull quina versió cridar
        if (Input.GetKeyDown(KeyCode.Space))
            Disparar();                        // versió 1: arma normal

        if (Input.GetKeyDown(KeyCode.Q))
            Disparar(TipusArma.Plasma);        // versió 2: arma especial

        if (Input.GetKeyDown(KeyCode.E))
            Disparar(3);                       // versió 3: ràfega de 3
    }
    // CONCEPTE 5: Overriding — el jugador té animació i perd punts
    public override void RebreDany(int quantitat) {
        base.RebreDany(quantitat); // executa la lògica de Nau
        Debug.Log("Jugador danyat!");
    }
    // CONCEPTE 5: Overriding — mort especial del jugador
    public override void Morir() {
        Debug.Log($"GAME OVER — Puntuació final: {Puntuacio}");
        Estat = EstatNau.Destruida; // CONCEPTE 1: enumeració
        // Aquí cridaries el GameManager
        Destroy(gameObject);
    }
    public void AfegirPunts(int punts) {
        Puntuacio += punts;
        Debug.Log($"Puntuació: {Puntuacio}");
    }
}