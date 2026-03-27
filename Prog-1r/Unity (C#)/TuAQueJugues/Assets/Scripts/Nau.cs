using UnityEngine;

// CONCEPTE 3: Her�ncia
// Nau �s la classe BASE. NauJugador i NauEnemiga hereten d'ella.
// Cont� tot el que �s COM� a totes les naus.
public class Nau : MonoBehaviour {
    public string NomNau;
    public int Vida;
    public int VidaMaxima;
    public TipusArma Arma;       // CONCEPTE 1: usa l'enumeraci�
    public EstatNau Estat;       // CONCEPTE 1: usa l'enumeraci�
    public GameObject PrefabBala;
    public Transform PuntDeDisparo;
    protected virtual void Start() {
        Vida = VidaMaxima;
        Estat = EstatNau.Volant;
        Debug.Log($"{NomNau} preparada! Arma: {Arma}");
    }
    // CONCEPTE 5: Overriding � virtual permet que les filles el reimplementin
    public virtual void RebreDany(int quantitat) {
        Vida -= quantitat;
        Estat = EstatNau.Danyada;
        Debug.Log($"{NomNau} rep {quantitat} de dany. Vida: {Vida}/{VidaMaxima}");
        if (Vida <= 0) Morir();
    }
    // CONCEPTE 5: Overriding � cada nau mor de forma diferent
    public virtual void Morir() {
        Estat = EstatNau.Destruida;
        Debug.Log($"{NomNau} destru�da!");
        Destroy(gameObject);
    }
    // CONCEPTE 2: Sobrec�rrega � versi� 1: dispara amb l'arma actual
    public virtual void Disparar() {
        if (PrefabBala == null || PuntDeDisparo == null) {
            Debug.LogWarning($"{NomNau}: assigna PrefabBala i PuntDeDisparo!");
            return;
        }
        Estat = EstatNau.Dispara;
        GameObject bala = Instantiate(PrefabBala, PuntDeDisparo.position, Quaternion.identity);
        Bala componentBala = bala.GetComponent<Bala>();
        if (componentBala != null) {
            // Passa el collider d'aquesta nau perqu� la bala l'ignori
            Collider2D propriCollider = GetComponent<Collider2D>();
            componentBala.Inicialitzar(this is NauJugador, propriCollider);
        }
    }
    // CONCEPTE 2: Sobrec�rrega � versi� 2: dispara amb una arma espec�fica
    public virtual void Disparar(TipusArma armaEspecifica) {
        TipusArma armaOriginal = Arma;  // guarda l'arma actual
        Arma = armaEspecifica;          // canvia temporalment
        Disparar();                     // reutilitza la l�gica base
        Arma = armaOriginal;            // restaura l'arma original
        Debug.Log($"{NomNau} dispara amb {armaEspecifica} (arma especial)!");
    }
    // CONCEPTE 2: Sobrec�rrega � versi� 3: dispara N vegades seguits
    public virtual void Disparar(int nombreDeDisparos) {
        for (int i = 0; i < nombreDeDisparos; i++) {
            Disparar();
            Debug.Log($"{NomNau} dispara r�fega {i + 1}/{nombreDeDisparos}!");
        }
    }
}