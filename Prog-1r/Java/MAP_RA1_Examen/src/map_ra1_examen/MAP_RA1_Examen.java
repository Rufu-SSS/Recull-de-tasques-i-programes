package map_ra1_examen;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class MAP_RA1_Examen {

    public static void main(String[] args) throws Exception {
        Document doc = loadXml("Jugadors.xml"); // carrega el fitxer XML anomenat Jugadors.xml
        if (doc == null) { // si no s'ha pogut llegir l'arxiu
            System.err.println("No s'ha pogut llegir Jugadors.xml. Col·loca el fitxer a la carpeta del projecte.");// missatge
            // d'error
            return; // surt del main
        }

        NodeList jugadores = doc.getElementsByTagName("jugador"); // obté tots els nodes <jugador> del document

        int opcio = 0; // variable per guardar l'opció de menú
        while (opcio != 5) { // bucle principal fins que l'usuari triï sortir
            opcio = menu(); // mostra el menú i llegeix una opció vàlida
            seleccio(opcio, jugadores); // processa l'opció escollida
        }

        System.out.println("Fins ara!"); // missatge final quan es surt del bucle
    }

    private static int menu() throws Exception { // mostra el menú i retorna una opció vàlida
        System.out.println("************************");
        System.out.println("******** MENÚ **********");
        System.out.println("1.- Mostrar jugador amb menys nivell");
        System.out.println("2.- Crear nou jugador");
        System.out.println("3.- ");
        System.out.println("4.- ");
        System.out.println("5.- Sortir");
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
                if (opcio >= 1 && opcio <= 4) { // comprova que estigui en l'interval permès
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
            case 1:
                printJugadorMenysNivell(jugadores); // Exercici 1
                break;
            case 2:
                crearNouJugador();
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
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

    private static void printJugadorMenysNivell(NodeList jugadores) {
        int minNivell = -1;
        int indexMax = -1;

        for (int i = 0; i < jugadores.getLength(); i++) {
            Element j = (Element) jugadores.item(i);
            String nivellText = getChildText(j, "nivell");
            int nivell = 0;
            try {
                nivell = Integer.parseInt(nivellText); // intenta convertir a enter
            } catch (NumberFormatException e) { // captura error de conversió
                // Si el valor no és numèric, el considerem 0
            }

            if (nivell > minNivell) {
                minNivell = nivell;
                indexMax = i;
            }
        }

        if (indexMax != -1) {
            System.out.println("Jugador amb menys nivell (" + minNivell + "):");
            printJugador(jugadores, indexMax);
        } else {
            System.out.println("No s'han pogut trobar jugadors vàlids amb nivell.");
        }
    }

    private static void printJugador(NodeList jugadores, int index) {
        // index
        if (index < 0 || index >= jugadores.getLength()) {
            System.out.println("Jugador no trobat.");
            return;
        }
        Element j = (Element) jugadores.item(index);
        String nom = getChildText(j, "nom");
        String nivell = getChildText(j, "nivell");
        String vida = getChildText(j, "vida");
        String atac = getChildText(j, "atac");
        String or = getChildText(j, "or");
        String defensa = getChildText(j, "defensa");
        String magia = getChildText(j, "magia");

        System.out.println("----- Jugador " + (index + 1) + " -----");
        System.out.println("Nom: " + nom);
        System.out.println("Nivell: " + nivell);
        System.out.println("Vida: " + vida);
        System.out.println("Atac: " + atac);
        System.out.println("Or: " + or);
        System.out.println("Defensa: " + defensa);
        System.out.println("Magia: " + magia);

        System.out.println("Inventari:"); // encapçalament de partides
        NodeList inventari = j.getElementsByTagName("objecte");
        for (int i = 0; i < inventari.getLength(); i++) {
            Element p = (Element) inventari.item(i);
            String nomObj = getChildText(p, "nomObj"); // llegeix <Data>
            String tipusObj = getChildText(p, "tipusObj"); // llegeix <Resultat>
            String nivellObj = getChildText(p, "nivellObj"); // llegeix <Durada>
            System.out.println("Nom: " + nomObj + " Tipus: " + tipusObj + " Nivell: "
                    + nivellObj);
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

    private static void crearNouJugador() throws TransformerException {
        try {
            Document doc = loadXml("./Jugadors.xml");
            if (doc == null) { // si no s'ha pogut carregar
                System.err.println("Error: No s'ha pogut carregar Jugadors.xml");
                return;
            }

            Scanner entrada = new Scanner(System.in);
            Node root = doc.getDocumentElement();

            Element jugador = doc.createElement("jugador");

            Element nom = doc.createElement("nom");
            System.out.println("Enta el nom del Jugador: ");
            String nomIn = entrada.nextLine();
            nom.setTextContent(nomIn);

            Element nivell = doc.createElement("nivell");
            System.out.println("Enta el nivell del Jugador: ");
            String lvlIn = entrada.nextLine();
            nivell.setTextContent(lvlIn);
            
            Element hp = doc.createElement("vida");
            System.out.println("Entra la vida que tindra el jugador: ");
            String hpIn = entrada.nextLine();
            hp.setTextContent(hpIn);
            
            Element atac = doc.createElement("atac");
            System.out.println("Entra l'atac que tindra el jugador: ");
            String atIn = entrada.nextLine();
            atac.setTextContent(atIn);

            Element or = doc.createElement("or");
            System.out.println("Entra l'or que tindra el jugador: ");
            String orIn = entrada.nextLine();
            or.setTextContent(orIn);

            Element defensa = doc.createElement("defensa");
            System.out.println("Entra la defensa que tindra el jugador: ");
            String dfIn = entrada.nextLine();
            defensa.setTextContent(dfIn);

            Element magia = doc.createElement("magia");
            System.out.println("Entra la magia que tindra el jugador: ");
            String mgIn = entrada.nextLine();
            magia.setTextContent(mgIn);

            jugador.appendChild(nom);
            jugador.appendChild(nivell);
            jugador.appendChild(atac);
            jugador.appendChild(or);
            jugador.appendChild(defensa);
            jugador.appendChild(magia);

            Element inventari = doc.createElement("inventari");
            Element objecte = doc.createElement("objecte");

            Element nomObj = doc.createElement("nomObj");
            System.out.println("Enta el nom del objecte: ");
            String objIn = entrada.nextLine();
            nomObj.setTextContent(objIn);

            Element tipusObj = doc.createElement("tipusObj");
            System.out.println("Enta el tipus d'objecte: ");
            String tipusIn = entrada.nextLine();
            tipusObj.setTextContent(tipusIn);

            Element nivellObj = doc.createElement("nivellObj");
            nivellObj.setTextContent("1");

            objecte.appendChild(nomObj);
            objecte.appendChild(tipusObj);
            objecte.appendChild(nivellObj);
            inventari.appendChild(objecte);
            jugador.appendChild(inventari);
            root.appendChild(jugador);

            System.out.println("Jugador creat correctament.");
            guardarXML(doc, "./Jugadors.xml");
        } catch (Exception e) {
            System.err.println("Error en crear el jugador nou: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void guardarXML(Document doc, String ruta)
            throws TransformerConfigurationException, TransformerException {
        // Usarem la classe Transformer per modificar arxius
        Transformer tFormer = TransformerFactory.newInstance().newTransformer();

        // Especifiquem el tipus de fitxer de sortida
        tFormer.setOutputProperty(OutputKeys.METHOD, "xml");

        // Formatem el fitxer XML per quan l'editem veurel bé
        tFormer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Crear un objecte Source especificant el fitxer d'origen
        // "doc" és el resultat de crear el document que hem usat en els exercicis
        // anteriors
        Source source = new DOMSource(doc);

        // Creem un objecte Result especificant el "destí"
        Result result = new StreamResult(new File(ruta));

        // Mescla l'origen amb el destí
        tFormer.transform(source, result);
    }
    
    
}
