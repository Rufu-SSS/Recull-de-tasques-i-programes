using UnityEngine;

// CONCEPTE 3: Herència — hereta de NauEnemiga (cadena d'herència!)
// CONCEPTE 4: Polimorfisme — és una Nau, però es comporta diferent

public class NauEnemigaElite : NauEnemiga {
    protected override void Start() {
        base.Start();
        NomNau = "Enemic Elit";
        VidaMaxima = 80;      // més vida
        Vida = VidaMaxima;
        PuntsAlMorir = 50;    // més punts
        Arma = TipusArma.Plasma; // CONCEPTE 1: arma diferent
        VelocitatBaixada = 1.2f;
    }
    // CONCEPTE 5: Overriding — rep menys dany (té escut)
    public override void RebreDany(int quantitat) {
        int danyReduit = quantitat / 2; // l'elit absorbeix la meitat
        Debug.Log($"Escut Elit! Dany reduït: {quantitat} → {danyReduit}");
        base.RebreDany(danyReduit);
    }
    // CONCEPTE 2: Sobrecàrrega — l'elit dispara en ràfega per defecte
    public override void Disparar() {
        Disparar(2); // crida la versió amb int heretada de Nau
    }
}