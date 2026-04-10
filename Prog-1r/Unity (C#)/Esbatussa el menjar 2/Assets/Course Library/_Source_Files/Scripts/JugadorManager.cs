using UnityEngine;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.IO;
using System.Linq;

public class JugadorManager : MonoBehaviour
{
    public LlistaJugadors llista = new LlistaJugadors();
    public int puntuacioDefault = 0;

    private string path;

    private void Awake()
    {
        path = Application.persistentDataPath + "/jugadors.xml";
        CarregarJugadors();
    }

    public void AfegirJugador(string nom, int puntuacio)
    {
        if (!string.IsNullOrEmpty(nom))
        {
            llista.Jugadors.Add(new Jugador(nom, puntuacio));
            OrdenarPerPuntuacio(llista.Jugadors);
            GuardarJugadors();
        }
    }

    public void OrdenarPerPuntuacio(List<Jugador> jugadors)
    {
        jugadors.Sort((a, b) => b.Puntuacio.CompareTo(a.Puntuacio));
    }

    public void GuardarJugadors()
    {
        XmlSerializer serializer = new XmlSerializer(typeof(LlistaJugadors));
        using (FileStream stream = new FileStream(path, FileMode.Create))
        {
            serializer.Serialize(stream, llista);
        }
    }

    public void CarregarJugadors()
    {
        if (File.Exists(path))
        {
            XmlSerializer serializer = new XmlSerializer(typeof(LlistaJugadors));
            using (FileStream stream = new FileStream(path, FileMode.Open))
            {
                llista = (LlistaJugadors)serializer.Deserialize(stream);
            }
        }
    }

    public void MostrarTop3()
    {
        OrdenarPerPuntuacio(llista.Jugadors);
        var top3 = llista.Jugadors.Take(3);
        foreach (var jugador in top3)
        {
            Debug.Log($"{jugador.Nom}: {jugador.Puntuacio}");
        }
    }
}