
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class MAP_RA1_P3_2 {

    public static void main(String[] args) throws Exception { // punt d'entrada; llença Exception per simplificar el
        // maneig d'errors
        Document doc = loadXml("clash.xml"); // carrega el fitxer XML anomenat clash.xml
        if (doc == null) { // si no s'ha pogut llegir l'arxiu
            System.err.println("No s'ha pogut llegir clash.xml. Col·loca el fitxer a la carpeta del projecte.");// missatge
            // d'error
            return; // surt del main
        }

        NodeList jugadores = doc.getElementsByTagName("Jugador"); // obté tots els nodes <Jugador> del document
        if (jugadores.getLength() < 3) { // comprova que n'hi hagi almenys 3
            System.err.println("No hi ha almenys 3 jugadors a clash.xml."); // avisa l'usuari
            return; // surt del main
        }

        int opcio = 0; // variable per guardar l'opció de menú
        while (opcio != 10) { // bucle principal fins que l'usuari triï sortir
            opcio = menu(); // mostra el menú i llegeix una opció vàlida
            seleccio(opcio, jugadores); // processa l'opció escollida
        }

        System.out.println("Fins ara!"); // missatge final quan es surt del bucle
    }

    private static int menu() throws Exception { // mostra el menú i retorna una opció vàlida
        System.out.println("************************");
        System.out.println("******** MENÚ **********");
        System.out.println("1.- Mostrar dades del jugador 1");
        System.out.println("2.- Mostrar dades del jugador 2");
        System.out.println("3.- Mostrar dades del jugador 3");
        System.out.println("4.- Tornar a mostrar el menú");
        System.out.println("5.- Mostrar jugador amb més copes");
        System.out.println("6.- Mostrar dades meteorològiques 2015");
        System.out.println("7.- Mostrar dades meteorològiques 2016");
        System.out.println("8.- Mostrar dades meteorològiques 2017");
        System.out.println("9.- Crear jugador nou");
        System.out.println("10.- Sortir");
        System.out.println("************************");

        BufferedReader consola = new BufferedReader(new InputStreamReader(System.in)); // reader per llegir línies de la
        // consola
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
                if (opcio >= 1 && opcio <= 10) { // comprova que estigui en l'interval permès
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

    private static void seleccio(int opcio, NodeList jugadores) throws TransformerException { // processa l'opció triada
        switch (opcio) {
            case 1: // si l'usuari ha triat l'opció 1, 2 o 3
            case 2:
            case 3:
                printJugador(jugadores, opcio - 1); // mostra les dades del jugador corresponent
                break;
            case 4: // només torna a mostrar el menú (no cal fer res aquí)
                break;
            case 5: // mostra el jugador amb més copes
                printJugadorMesCopes(jugadores);
                break;
            case 6: // processa dades meteorològiques de 2015
                processarFitxerMeteo("meteo2015.xml", 2015);
                break;
            case 7: // processa dades meteorològiques de 2016
                processarFitxerMeteo("meteo2016.xml", 2016);
                break;
            case 8: // processa dades meteorològiques de 2017
                processarFitxerMeteo("meteo2017.xml", 2017);
                break;
            case 9: // Crear jugador nou a XML
                crearNouJugador();
                break;
            case 10: // sortir
                System.out.println("Sortint..."); // missatge abans de sortir
                break;
            default:
                System.out.println("Opció no reconeguda."); // avís per opció no reconeguda
        }
    }

    private static Document loadXml(String path) { // carrega un fitxer XML
        try {
            File f = new File(path); // crea objecte File amb la ruta donada
            if (!f.exists()) {
                return null; // si el fitxer no existeix, retorna null
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); // crea una fàbrica de constructors de
            // documents
            DocumentBuilder db = dbf.newDocumentBuilder(); // crea un constructor de documents
            return db.parse(f); // parseja el fitxer i retorna el document resultant
        } catch (Exception e) { // captura qualsevol excepció durant la càrrega
            e.printStackTrace(); // imprimeix la pila d'errors per depuració
            return null; // en cas d'error, retorna null
        }
    }

    private static void printJugadorMesCopes(NodeList jugadores) { // troba i imprimeix el jugador amb més copes
        int maxCopes = -1;
        int indexMax = -1;

        for (int i = 0; i < jugadores.getLength(); i++) {
            Element j = (Element) jugadores.item(i);
            String copesText = getChildText(j, "Copes"); // llegeix el text de <Copes>
            int copes = 0;
            try {
                copes = Integer.parseInt(copesText); // intenta convertir a enter
            } catch (NumberFormatException e) { // captura error de conversió
                // Si el valor no és numèric, el considerem 0
            }

            if (copes > maxCopes) { // si aquest jugador té més copes que el màxim actual
                maxCopes = copes; // actualitza el màxim
                indexMax = i; // guarda l'índex del jugador
            }
        }

        if (indexMax != -1) { // si s'ha trobat almenys un jugador vàlid
            System.out.println("Jugador amb més copes (" + maxCopes + "):");
            printJugador(jugadores, indexMax); // imprimeix les dades del jugador amb més copes
        } else {
            System.out.println("No s'han pogut trobar jugadors vàlids amb copes.");
        }
    }

    private static void printJugador(NodeList jugadores, int index) { // imprimeix la info del jugador en la posició
        // index
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
            System.out.println("  Partida " + (i + 1) + ": Data: " + data + ", Resultat: " + resultat + ", Durada: "
                    + durada + ", Tipus: " + tipus); // imprimeix resum de la partida
        }
        System.out.println("---------------------------"); // separador final
    }

    private static String getChildText(Element parent, String tag) { // obté el text del primer fill amb el tag donat
        NodeList nl = parent.getElementsByTagName(tag); // obté la llista de nodes amb el tag dins del parent
        if (nl.getLength() == 0) {
            return ""; // si no hi ha cap, retorna cadena buida
        }
        return nl.item(0).getTextContent().trim(); // retorna el text del primer node, sense espais al voltant
    }

    private static void processarFitxerMeteo(String fitxer, int any) { // processa dades meteorològiques d'un fitxer
        Document doc = loadXml(fitxer); // carrega el fitxer XML
        if (doc == null) { // si no s'ha pogut carregar
            System.out.println("No s'ha pogut carregar el fitxer " + fitxer);
            return;
        }

        NodeList elements = doc.getElementsByTagName("element"); // obté tots els nodes <element>

        double tmax = Double.NEGATIVE_INFINITY; // inicialitza temperatura màxima
        double tmin = Double.POSITIVE_INFINITY; // inicialitza temperatura mínima

        String diaMax = "", horaMax = ""; // variables per guardar dia i hora de màximes i mínimes
        String diaMin = "", horaMin = ""; // variables per guardar dia i hora de màximes i mínimes

        for (int i = 0; i < elements.getLength(); i++) { // itera tots els elements
            Element e = (Element) elements.item(i); // cada element

            String tmaxStr = getChildText(e, "tmax").replace(",", "."); // llegeix tmax i substitueix comes per punts
            String tminStr = getChildText(e, "tmin").replace(",", "."); // llegeix tmin i substitueix comes per punts
            String data = getChildText(e, "fecha"); // llegeix data
            String horaTmax = getChildText(e, "horatmax"); // llegeix hora tmax
            String horaTmin = getChildText(e, "horatmin"); // llegeix hora tmin

            try { // intenta convertir les temperatures a double
                double tx = Double.parseDouble(tmaxStr); // temperatura màxima
                double tn = Double.parseDouble(tminStr); // temperatura mínima

                if (tx > tmax) { // si aquesta tmax és més gran que la màxima actual
                    tmax = tx; // actualitza tmax
                    diaMax = data; // actualitza dia
                    horaMax = horaTmax; // actualitza hora
                }

                if (tn < tmin) { // si aquesta tmin és més petita que la mínima actual
                    tmin = tn; // actualitza tmin
                    diaMin = data; // actualitza dia
                    horaMin = horaTmin; // actualitza hora
                }

            } catch (NumberFormatException ex) { // captura error de conversió
                // ignore
            }
        }

        System.out.println("---- Dades Meteorològiques " + any + " ----");
        System.out.println("Temperatura màxima: " + tmax + "°C el " + diaMax + " a les " + horaMax);
        System.out.println("Temperatura mínima: " + tmin + "°C el " + diaMin + " a les " + horaMin);
        System.out.println("--------------------------------------------");
    }

    private static void crearNouJugador() throws TransformerException {
        try {
            Document doc = loadXml("./clash.xml");
            if (doc == null) { // si no s'ha pogut carregar
                System.err.println("Error: No s'ha pogut carregar clash.xml");
                return;
            }

            Scanner entrada = new Scanner(System.in);
            Node Jugadors = doc.getDocumentElement();

            Element jugador = doc.createElement("Jugador"); // <Jugador><\Jugador>

            Element nom = doc.createElement("Nom"); // <Nom><\Nom>
            System.out.println("Enta el nom del Jugador: ");
            String nomIn = entrada.nextLine();
            nom.setTextContent(nomIn);

            Element nivell = doc.createElement("Nivell"); // <Nivell><\Nivell>
            nivell.setTextContent("0");

            Element copes = doc.createElement("Copes"); // <Copes><\Copes>
            copes.setTextContent("0");

            Element oro = doc.createElement("Oro"); // <Oro><\Oro>
            oro.setTextContent("0");

            Element gemes = doc.createElement("Gemes"); // <Estrelles><\Estrelles>
            gemes.setTextContent("0");

            Element estrelles = doc.createElement("Estrelles"); // <Estrelles><\Estrelles>
            estrelles.setTextContent("0");

            jugador.appendChild(nom);
            jugador.appendChild(nivell);
            jugador.appendChild(copes);
            jugador.appendChild(oro);
            jugador.appendChild(gemes);
            jugador.appendChild(estrelles);
            Jugadors.appendChild(jugador);

            guardarXML(doc, "./clash.xml");
        } catch (Exception e) {
            System.err.println("Error en crear el jugador nou: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void guardarXML(Document doc, String ruta) throws TransformerConfigurationException, TransformerException {
        // Usarem la classe Transformer per modificar arxius
        Transformer tFormer = TransformerFactory.newInstance().newTransformer();

        // Especifiquem el tipus de fitxer de sortida
        tFormer.setOutputProperty(OutputKeys.METHOD, "xml");

        // Formatem el fitxer XML per quan l'editem veurel bé
        tFormer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Crear un objecte Source especificant el fitxer d'origen
        // "doc" és el resultat de crear el document que hem usat en els exercicis anteriors
        Source source = new DOMSource(doc);

        // Creem un objecte Result especificant el "destí"
        Result result = new StreamResult(new File(ruta));

        // Mescla l'origen amb el destí
        tFormer.transform(source, result);
    }

    private static void seleccioAleatoria() {
        try {
            Document doc = loadXml("./clash.xml");
            if (doc == null) { // si no s'ha pogut carregar
                System.err.println("Error: No s'ha pogut carregar clash.xml");
                return;
            }
            NodeList jugadors = doc.getElementsByTagName("Jugador"); // Obtenir la llista de "Jugador"
            if (jugadors.getLength() < 2) {
                System.out.println("No hi han prous jugadors per seleccionar a 2 aleatoris.");
            }
            // Crear un objecte Random per seleccionar jugadors aleatoris
            Random random = new Random();

            int jugadorRandom1 = random.nextInt(jugadors.getLength());
            int jugadorRandom2;
            do {
                jugadorRandom2 = random.nextInt(jugadors.getLength());
            } while (jugadorRandom1 == jugadorRandom2);

            System.out.println("Jugador 1 seleccionat:");
            printJugador(jugadors, jugadorRandom1);

            System.out.println("Jugador 2 seleccionat:");
            printJugador(jugadors, jugadorRandom2);
            
            

        } catch (Exception e) {
            System.out.println("Error en seleccionar els jugadors aleatoris: " + e.getMessage());
        }
    }

}
