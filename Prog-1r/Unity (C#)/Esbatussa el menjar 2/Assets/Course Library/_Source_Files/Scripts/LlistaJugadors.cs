using System.Collections.Generic;
using System.Xml.Serialization;

[System.Serializable]
[XmlRoot("LlistaJugadors")]
public class LlistaJugadors
{
    [XmlElement("Jugador")]
    public List<Jugador> Jugadors = new List<Jugador>();
}