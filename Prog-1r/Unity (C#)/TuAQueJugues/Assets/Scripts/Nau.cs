using UnityEngine;

// CONCEPTE 3: Herència
// Nau és la classe BASE. NauJugador i NauEnemiga hereten d'ella.
// Conté tot el que és COMÚ a totes les naus.
public class Nau : MonoBehaviour
{
    public string NomNau;
    public int Vida;
    public int VidaMaxima;
    public TipusArma Arma;       // CONCEPTE 1: usa l'enumeració
    public EstatNau Estat;       // CONCEPTE 1: usa l'enumeració
    public GameObject PrefabBala;
    public Transform PuntDeDisparo;

    protected virtual void Start()
    {
        Vida = VidaMaxima;
        Estat = EstatNau.Volant;
        Debug.Log($"{NomNau} preparada! Arma: {Arma}");
    }

    // CONCEPTE 5: Overriding — virtual permet que les filles el reimplementin
    public virtual void RebreDany(int quantitat)
    {
        Vida -= quantitat;
        Estat = EstatNau.Danyada;
        Debug.Log($"{NomNau} rep {quantitat} de dany. Vida: {Vida}/{VidaMaxima}");
        if (Vida <= 0) Morir();
    }

    // CONCEPTE 5: Overriding — cada nau mor de forma diferent
    public virtual void Morir()
    {
        Estat = EstatNau.Destruida;
        Debug.Log($"{NomNau} destruïda!");
        Destroy(gameObject);
    }

    // CONCEPTE 2: Sobrecàrrega — versió 1: dispara amb l'arma actual
    public virtual void Disparar()
    {
        if (PrefabBala == null || PuntDeDisparo == null)
        {
            Debug.LogWarning($"{NomNau}: assigna PrefabBala i PuntDeDisparo a l'Inspector!");
            return;
        }

        Estat = EstatNau.Dispara;
        GameObject bala = Instantiate(PrefabBala, PuntDeDisparo.position, Quaternion.identity);
        Bala componentBala = bala.GetComponent<Bala>();

        if (componentBala != null)
            componentBala.EsDelJugador = this is NauJugador;

        Debug.Log($"{NomNau} dispara amb {Arma}!");
    }

    // CONCEPTE 2: Sobrecàrrega — versió 2: dispara amb una arma específica
    public virtual void Disparar(TipusArma armaEspecifica)
    {
        TipusArma armaOriginal = Arma;  // guarda l'arma actual
        Arma = armaEspecifica;          // canvia temporalment
        Disparar();                     // reutilitza la lògica base
        Arma = armaOriginal;            // restaura l'arma original
        Debug.Log($"{NomNau} dispara amb {armaEspecifica} (arma especial)!");
    }

    // CONCEPTE 2: Sobrecàrrega — versió 3: dispara N vegades seguits
    public virtual void Disparar(int nombreDeDisparos)
    {
        for (int i = 0; i < nombreDeDisparos; i++)
        {
            Disparar();
            Debug.Log($"{NomNau} dispara ràfega {i + 1}/{nombreDeDisparos}!");
        }
    }
}