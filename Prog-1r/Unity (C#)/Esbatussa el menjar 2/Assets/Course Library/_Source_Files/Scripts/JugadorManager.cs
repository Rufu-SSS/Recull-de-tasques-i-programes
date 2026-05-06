using UnityEngine;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.IO;
using System.Linq;

public class JugadorManager : MonoBehaviour
{
    public LlistaJugadors llista = new LlistaJugadors();
    private string _path;

    private void Awake()
    {
        _path = Path.Combine(Application.persistentDataPath, "jugadors.xml");
        CarregarJugadors();
    }

    public void AfegirJugador(string nom, int puntuacio)
    {

        Debug.Log($"Jugador afegit/actualitzat: {nom} - {puntuacio} pts");
        if (string.IsNullOrEmpty(nom))
        {
            Debug.LogWarning("El nom del jugador és buit!");
            return;
        }

        Jugador existent = llista.Jugadors.Find(j => j.Nom == nom);
        if (existent != null)
        {
            if (puntuacio > existent.Puntuacio)
                existent.Puntuacio = puntuacio;
        }
        else
        {
            llista.Jugadors.Add(new Jugador(nom, puntuacio));
        }

        OrdenarPerPuntuacio();
        GuardarJugadors();
    }

    public void OrdenarPerPuntuacio()
    {
        llista.Jugadors.Sort((a, b) => b.Puntuacio.CompareTo(a.Puntuacio));
    }

    public void GuardarJugadors()
    {
        try
        {
            XmlSerializer serializer = new XmlSerializer(typeof(LlistaJugadors));
            using (FileStream stream = new FileStream(_path, FileMode.Create))
            {
                serializer.Serialize(stream, llista); // ← usa "llista" directament
            }
            Debug.Log($"Jugadors guardats a: {_path}");
        }
        catch (System.Exception e)
        {
            Debug.LogError($"Error guardant jugadors: {e.Message}");
        }
    }

    public void CarregarJugadors()
    {
        if (!File.Exists(_path))
        {
            Debug.Log("No hi ha fitxer, es crearà quan es guardi el primer jugador.");
            return;
        }

        try
        {
            XmlSerializer serializer = new XmlSerializer(typeof(LlistaJugadors));
            using (FileStream stream = new FileStream(_path, FileMode.Open))
            {
                llista = (LlistaJugadors)serializer.Deserialize(stream);
            }
            Debug.Log($"Carregats {llista.Jugadors.Count} jugadors.");
        }
        catch (System.Exception e)
        {
            Debug.LogError($"Error carregant jugadors: {e.Message}");
            llista = new LlistaJugadors();
        }
    }

    public List<Jugador> GetTop(int n = 3)
    {
        OrdenarPerPuntuacio();
        return llista.Jugadors.Take(n).ToList();
    }

    public void MostrarTop3()
    {
        List<Jugador> top3 = GetTop(3);
        Debug.Log("=== TOP 3 ===");
        for (int i = 0; i < top3.Count; i++)
            Debug.Log($"{i + 1}. {top3[i]}");
    }
}