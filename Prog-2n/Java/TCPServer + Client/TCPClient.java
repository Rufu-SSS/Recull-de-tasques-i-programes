import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 8080);
        System.out.println("Connectat al servidor!");
        System.out.println("Escriu missatges (escriu 'exit' per sortir)\n");

        PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader entrada = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
        
        Scanner scanner = new Scanner(System.in);

        String missatge;
        while (true) {
            System.out.print("Tu: ");
            missatge = scanner.nextLine();

            sortida.println(missatge);

            if (missatge.equalsIgnoreCase("exit")) {
                String resposta = entrada.readLine();
                System.out.println("Servidor: " + resposta);
                break;
            }

            String resposta = entrada.readLine();
            System.out.println("Servidor: " + resposta + "\n");
        }

        scanner.close();
        socket.close();
        System.out.println("Desconnectat del servidor");
    }
}