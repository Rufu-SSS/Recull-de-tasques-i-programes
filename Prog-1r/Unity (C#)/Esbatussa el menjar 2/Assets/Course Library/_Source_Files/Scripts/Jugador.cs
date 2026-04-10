[System.Serializable]
public class Jugador
{
    public string Nom;
    public int Puntuacio;

    public Jugador() { }

    public Jugador(string nom, int puntuacio)
    {
        Nom = nom;
        Puntuacio = puntuacio;
    }
}
