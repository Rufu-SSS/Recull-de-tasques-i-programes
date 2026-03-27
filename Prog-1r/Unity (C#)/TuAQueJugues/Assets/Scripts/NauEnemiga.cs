using UnityEngine;

// CONCEPTE 3: Herència — NauEnemiga HERETA de Nau
// CONCEPTE 5: Overriding — comportament diferent al morir

public class NauEnemiga : Nau {
    public int PuntsAlMorir = 10;
    public float VelocitatBaixada = 2f;
    private NauJugador _jugador;
    protected override void Start() {
        NomNau = "Enemic Bàsic";
        VidaMaxima = 30;
        Arma = TipusArma.Missile; // CONCEPTE 1: enumeració
        base.Start();
        _jugador = FindObjectOfType<NauJugador>();
    }
    void Update() {
        // L'enemic baixa cap al jugador
        transform.Translate(Vector3.down * VelocitatBaixada * Time.deltaTime);
        // Dispara automàticament cada cert temps
        if (Random.value < 0.001f)
            Disparar(); // CONCEPTE 2: sobrecàrrega versió 1
    }
    // CONCEPTE 5: Overriding — en morir dona punts al jugador
    public override void Morir() {
        Debug.Log($"{NomNau} destruït! +{PuntsAlMorir} punts");
        _jugador?.AfegirPunts(PuntsAlMorir);
        Estat = EstatNau.Destruida; // CONCEPTE 1: enumeració
        Destroy(gameObject);
    }
}