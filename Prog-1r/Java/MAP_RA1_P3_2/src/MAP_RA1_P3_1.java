import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class MAP_RA1_P3_1 {

    public static void main(String[] args) throws Exception { // punt d'entrada; llença Exception per simplificar el maneig d'errors
        Document doc = loadXml("clash.xml"); // carrega el fitxer XML anomenat clash.xml
        if (doc == null) { // si no s'ha pogut llegir l'arxiu
            System.err.println("No s'ha pogut llegir clash.xml. Col·loca el fitxer a la carpeta del projecte."); // missatge d'error
            return; // surt del main
        }

        NodeList jugadores = doc.getElementsByTagName("Jugador"); // obté tots els nodes <Jugador> del document
        if (jugadores.getLength() < 3) { // comprova que n'hi hagi almenys 3
            System.err.println("No hi ha almenys 3 jugadors a clash.xml."); // avisa l'usuari
            return; // surt del main
        }

        int opcio = 0; // variable per guardar l'opció de menú
        while (opcio != 9) { // bucle principal fins que l'usuari triï sortir
            opcio = menu(); // mostra el menú i llegeix una opció vàlida
            seleccio(opcio, jugadores); // processa l'opció escollida
        }

        System.out.println("Fins ara!"); // missatge final quan es surt del bucle
    }

    private static int menu() throws Exception { 
        System.out.println("************************");
        System.out.println("******** MENÚ **********");
        System.out.println("1.- Mostrar dades del jugador 1");
        System.out.println("2.- Mostrar dades del jugador 2");
        System.out.println("3.- Mostrar dades del jugador 3");
        System.out.println("4.- Tornar a mostrar el menú");
        System.out.println("5.- Mostrar jugador amb més copes");
        System.out.println("6.- Mostrar temperatures màximes i mínimes del 2015");
        System.out.println("7.- Mostrar temperatures màximes i mínimes del 2016");
        System.out.println("8.- Mostrar temperatures màximes i mínimes del 2017");
        System.out.println("9.- Sortir");
        System.out.println("************************");

        BufferedReader consola = new BufferedReader(new InputStreamReader(System.in)); // reader per llegir línies de la consola
        int opcio = 0; // valor per retornar
        while (true) { // bucle fins a obtenir una entrada vàlida
            System.out.print(" --Escull Opció: "); // prompt per l'usuari
            String liniaEntrada = consola.readLine(); // llegeix una línia de la consola

            if (liniaEntrada == null || liniaEntrada.trim().isEmpty()) { // si és nul·la o buida
                System.out.println("Opció buida. Torna-ho a intentar."); // avisa i continua el bucle
                continue;
            }

            try {
                opcio = Integer.parseInt(liniaEntrada.trim()); // intenta convertir l'entrada a enter
                if (opcio >= 1 && opcio <= 9) { // comprova que estigui en l'interval permès
                    break; // opció vàlida, surt del bucle
                } else {
                    System.out.println("Opció no reconeguda. Torna-ho a intentar."); // avís si està fora de el rang
                }
            } catch (NumberFormatException e) { // si la conversió falla
                System.out.println("Entrada no vàlida. Si us plau, introdueix un número."); // avís per l'usuari
            }
        }
        return opcio; // retorna l'opció seleccionada
    }

    private static void seleccio(int opcio, NodeList jugadores) { // processa l'opció triada
        switch (opcio) {
            case 1: // si l'usuari ha triat l'opció 1, 2 o 3
            case 2:
            case 3:
                printJugador(jugadores, opcio - 1); // mostra les dades del jugador corresponent
                break;
            case 4: // només torna a mostrar el menú (no cal fer res aquí)
                break;
            case 5: // Mostra el jugador amb més copes
                mostrarJugadorAmbMesCopes(jugadores);
                break;
            case 6:
                mostrarTemperaturesPerAny("meteo2015.xml");
                break;
            case 7:
                mostrarTemperaturesPerAny("meteo2016.xml");
                break;
            case 8:
                mostrarTemperaturesPerAny("meteo2017.xml");
                break;
            case 9:
                System.out.println("Sortint del programa...");
                break;
            default:
                System.out.println("Opció no reconeguda.");
        }
    }

    private static Document loadXml(String path) { // carrega un fitxer XML
        try {
            File f = new File(path); // crea objecte File amb la ruta donada
            if (!f.exists()) return null; // si el fitxer no existeix, retorna null
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); // crea una fàbrica de constructors de documents
            DocumentBuilder db = dbf.newDocumentBuilder(); // crea un constructor de documents
            return db.parse(f); // parseja el fitxer i retorna el document resultant
        } catch (Exception e) { // captura qualsevol excepció durant la càrrega
            e.printStackTrace(); // imprimeix la pila d'errors per depuració
            return null; // en cas d'error, retorna null
        }
    }

        private static void mostrarJugadorAmbMesCopes(NodeList jugadores) {
        // Troba el jugador amb més copes
        int jugadorMaxCopesIndex = findJugadorWithMostCups(jugadores);
        
        if (jugadorMaxCopesIndex != -1) {
            // Mostra les dades del jugador amb més copes
            printJugador(jugadores, jugadorMaxCopesIndex);
        } else {
            System.out.println("No s'ha trobat cap jugador amb copes.");
        }
    }

    private static int findJugadorWithMostCups(NodeList jugadores) {
        int maxCopes = -1;
        int maxCopesIndex = -1;
        
        // Itera per tots els jugadors per trobar el de més copes
        for (int i = 0; i < jugadores.getLength(); i++) {
            Element jugador = (Element) jugadores.item(i);
            String copesString = getChildText(jugador, "Copes");
            try {
                int copes = Integer.parseInt(copesString); // converteix el número de copes a enter
                if (copes > maxCopes) {
                    maxCopes = copes;
                    maxCopesIndex = i;
                }
            } catch (NumberFormatException e) {
                System.err.println("Error en convertir el número de copes del jugador " + (i + 1));
            }
        }
        return maxCopesIndex; // retorna l'índex del jugador amb més copes
    }
    
    private static void printJugador(NodeList jugadores, int index) { // imprimeix la info del jugador en la posició index
        if (index < 0 || index >= jugadores.getLength()) { // comprova límits
            System.out.println("Jugador no trobat."); // avisa si l'índex no és vàlid
            return;
        }
        Element j = (Element) jugadores.item(index); // obté l'element Jugador corresponent
        String nom = getChildText(j, "Nom"); // llegeix <Nom>
        String nivell = getChildText(j, "Nivell"); // llegeix <Nivell>
        String copes = getChildText(j, "Copes"); // llegeix <Copes>
        String oro = getChildText(j, "Oro"); // llegeix <Oro>
        String gemes = getChildText(j, "Gemes"); // llegeix <Gemes>
        String estrelles = getChildText(j, "Estrelles"); // llegeix <Estrelles>

        System.out.println("----- Jugador " + (index + 1) + " -----"); // encapçalament (+1 per mostrar 1..n)
        System.out.println("Nom: " + nom); // imprimeix nom
        System.out.println("Nivell: " + nivell); // imprimeix nivell
        System.out.println("Copes: " + copes); // imprimeix copes
        System.out.println("Oro: " + oro); // imprimeix orro
        System.out.println("Gemes: " + gemes); // imprimeix gemes
        System.out.println("Estrelles: " + estrelles); // imprimeix estrelles

        System.out.println("Partides:"); // encapçalament de partides
        NodeList partides = j.getElementsByTagName("Partida"); // obté totes les <Partida> dins del jugador
        for (int i = 0; i < partides.getLength(); i++) { // itera les partides
            Element p = (Element) partides.item(i); // cada element Partida
            String data = getChildText(p, "Data"); // llegeix <Data>
            String resultat = getChildText(p, "Resultat"); // llegeix <Resultat>
            String durada = getChildText(p, "Durada"); // llegeix <Durada>
            String tipus = getChildText(p, "Tipus"); // llegeix <Tipus>
            System.out.println("  Partida " + (i + 1) + ": Data: " + data + ", Resultat: " + resultat + ", Durada: " + durada + ", Tipus: " + tipus); // imprimeix resum de la partida
        }
        System.out.println("---------------------------"); // separador final
    }

    private static String getChildText(Element parent, String tag) { // obté el text del primer fill amb el tag donat
        NodeList nl = parent.getElementsByTagName(tag); // obté la llista de nodes amb el tag dins del parent
        if (nl.getLength() == 0) return ""; // si no hi ha cap, retorna cadena buida
        return nl.item(0).getTextContent().trim(); // retorna el text del primer node, sense espais al voltant
    }
    
    private static void mostrarTemperaturesPerAny(String fitxer) {
        Document doc = loadXml(fitxer); // Carrega el fitxer XML meteorològic
        if (doc == null) {
            System.err.println("No s'ha pogut llegir el fitxer: " + fitxer);
            return;
        }

        NodeList registres = doc.getElementsByTagName("element"); // Obté tots els nodes <element>
        if (registres.getLength() == 0) {
            System.err.println("No hi ha dades en el fitxer " + fitxer);
            return;
        }

        // Variables per guardar les temperatures extrems
        double tmax = Double.NEGATIVE_INFINITY; // valor inicial molt baix
        double tmin = Double.POSITIVE_INFINITY; // valor inicial molt alt
        String dataTmax = "", horaTmax = "", dataTmin = "", horaTmin = "";

        // Recorrem tots els registres meteorològics
        for (int i = 0; i < registres.getLength(); i++) {
            Element e = (Element) registres.item(i);
            String data = getChildText(e, "fecha");
            String horaMax = getChildText(e, "horatmax");
            String horaMin = getChildText(e, "horatmin");
            String tmaxStr = getChildText(e, "tmax");
            String tminStr = getChildText(e, "tmin");

            try {
                // Convertim a double (per si s’usen comes en lloc de punts)
                double tmaxDia = Double.parseDouble(tmaxStr.replace(',', '.'));
                double tminDia = Double.parseDouble(tminStr.replace(',', '.'));

                // Actualitzem valors màxims i mínims si cal
                if (tmaxDia > tmax) {
                    tmax = tmaxDia;
                    dataTmax = data;
                    horaTmax = horaMax;
                }
                if (tminDia < tmin) {
                    tmin = tminDia;
                    dataTmin = data;
                    horaTmin = horaMin;
                }
            } catch (Exception ex) {
                // Si hi ha un valor incorrecte, l’ignorem
            }
        }

        // Extreiem l’any del nom del fitxer (per exemple "meteo2015.xml" → "2015")
        String any = fitxer.replaceAll("\\D+", "");

        // Mostrem els resultats finals per pantalla
        System.out.println("\nTemperatures Meteorològiques " + any);
        System.out.println("Temperatura màxima (" + dataTmax + " " + horaTmax + ") = " + tmax + " °C");
        System.out.println("Temperatura mínima (" + dataTmin + " " + horaTmin + ") = " + tmin + " °C");
    }
}
