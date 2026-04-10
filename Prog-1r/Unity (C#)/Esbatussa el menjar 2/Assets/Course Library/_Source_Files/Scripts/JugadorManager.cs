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
        if (string.IsNullOrEmpty(nom))
        {
            Debug.LogWarning("El nom del jugador és buit!");
            return;
        }

        // Si el jugador ja existeix, actualitza la puntuació si és més alta
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
        Debug.Log($"Jugador afegit/actualitzat: {nom} - {puntuacio} pts");
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
                serializer.Serialize(stream, llista);
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
            Debug.Log("No hi ha fitxer de jugadors, es crearà un de nou.");
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
            llista = new LlistaJugadors(); // reset si el fitxer està corrupte
        }
    }

    // Retorna el top N jugadors (per defecte 3)
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