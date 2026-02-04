package map_ra2_p_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.io.InputStreamReader;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author AluCiclesGS1
 * //Per a poder fer servir la pràctica sense errors s'ha de crear manualment una base
 *   de dades a PostgreSQL de nom clash amb usuari postgres i contrasenya accedir.
 */
public class MAP_RA2_P_1 {

    public static void main(String[] args) throws SQLException, Exception {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }

        Connection connection = null;
        // Database connect
        // Conectem amb la base de dades
        connection = (Connection) DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5433/clash", "postgres", "accedir");
                
        int opcio = 0; // variable per guardar l'opció de menú
        while (opcio != 7) { // bucle principal fins que l'usuari triï sortir
            opcio = menu(); // mostra el menú i llegeix una opció vàlida
            seleccio(opcio, connection); // processa l'opció escollida
        }

        System.out.println("Fins ara!"); // missatge final quan es surt del bucle
        connection.close();
    }

    private static int menu() throws Exception { // mostra el menú i retorna una opció vàlida

        System.out.println("------------------------");
        System.out.println("******** MENÚ **********");
        System.out.println("1.- Exercici 1");
        System.out.println("2.- Exercici 2");
        System.out.println("3.- Exercici 3");
        System.out.println("4.- Exercici 4");
        System.out.println("5.- Exercici 5");
        System.out.println("6.- Exercici 6");
        System.out.println("7.- Sortir");
        System.out.println("------------------------");

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
                if (opcio >= 1 && opcio <= 7) { // comprova que estigui en l'interval permès
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

    private static void seleccio(int opcio, Connection connection) throws SQLException, IOException { // processa l'opció triada
        switch (opcio) {

            case 1:
                exercici1(connection);
                break;
            case 2:
                exercici2(connection);
                break;
            case 6:
                exercici6(connection);
                break;
            case 7: // sortir
                System.out.println("Sortint..."); // missatge abans de sortir
                break;
            default:
                System.out.println("Opció no reconeguda."); // avís per opció no reconeguda
        }
    }

    private static void exercici1(Connection connection) throws SQLException {

        Statement stmJug = connection.createStatement();
        ResultSet rsJug = stmJug.executeQuery("SELECT * FROM jugadors");

        while (rsJug.next()) {

            int idJugador = rsJug.getInt("id");
            String nom = rsJug.getString("nom");
            int copes = rsJug.getInt("copes");
            int nivell = rsJug.getInt("nivell");

            System.out.println("\nJugador: " + nom + " (ID: " + idJugador + ")");
            System.out.println("Copes: " + copes + " | Nivell: " + nivell);
            System.out.println("Partides:");

            PreparedStatement psPartides = connection.prepareStatement(
                    "SELECT * FROM partides WHERE idjug1 = ? OR idjug2 = ?"
            );

            psPartides.setInt(1, idJugador);
            psPartides.setInt(2, idJugador);

            ResultSet rsPart = psPartides.executeQuery();

            boolean tePartides = false;

            while (rsPart.next()) {
                tePartides = true;

                int idPartida = rsPart.getInt("id");
                int idJug1 = rsPart.getInt("idjug1");
                int idJug2 = rsPart.getInt("idjug2");
                String resultat = rsPart.getString("resultat");
                String tipus = rsPart.getString("tipus");
                String data = rsPart.getString("temps");

                System.out.println("- Partida #" + idPartida
                        + " | Jugadors: " + idJug1 + " vs " + idJug2
                        + " | Data: " + data
                        + " | Tipus: " + tipus
                        + " | Resultat: " + resultat);
            }

            if (!tePartides) {
                System.out.println("  (Aquest jugador no ha jugat cap partida)");
            }

            rsPart.close();
            psPartides.close();
        }

        rsJug.close();
        stmJug.close();
    }

    private static void exercici2(Connection connection) throws SQLException, IOException {

        BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));

        // 1. Demanar nom
        System.out.print("Introdueix el nom del jugador: ");
        String nom = consola.readLine();

        // 2. Validació
        if (nom == null || nom.trim().isEmpty()) {
            System.out.println("El nom no pot estar buit.");
            return;
        }

        // 3. Obtenir últim ID
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT COALESCE(MAX(id), 0) AS ultimId FROM jugadors");

        int nouId = 1;
        if (rs.next()) {
            nouId = rs.getInt("ultimId") + 1;
        }

        // 4. INSERT
        PreparedStatement insertJug = connection.prepareStatement(
                "INSERT INTO jugadors (id, nom, nivell, oro, gemes, copes) VALUES (?, ?, ?, ?, ?, ?)");

        insertJug.setInt(1, nouId);
        insertJug.setString(2, nom);
        insertJug.setInt(3, 1);   // nivell per defecte
        insertJug.setInt(4, 0);   // oro
        insertJug.setInt(5, 0);   // gemes
        insertJug.setInt(6, 0);   // copes

        insertJug.executeUpdate();

        // 5. Missatge final
        System.out.println("Jugador " + nom + " afegit correctament amb ID " + nouId);

        // 6. Tancar recursos
        rs.close();
        stm.close();
        insertJug.close();
    }

    private static void exercici6(Connection connection) throws SQLException {
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM jugadors");

        while (rs.next()) {
            System.out.println("Jugador Nom: " + rs.getString("nom"));
        }

        PreparedStatement insertJug = connection.prepareStatement(
                "INSERT INTO jugadors(id, nom, nivell, oro, gemes, copes) VALUES (?, ?, ?, ?, ?, ?)");

        insertJug.setInt(1, 3);
        insertJug.setString(2, "xavier");
        insertJug.setInt(3, 1);
        insertJug.setInt(4, 200);
        insertJug.setInt(5, 30);
        insertJug.setInt(6, 1000);
        insertJug.executeUpdate();
    }
}
