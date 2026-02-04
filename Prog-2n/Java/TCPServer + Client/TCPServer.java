import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Servidor TCP escoltant al port 8080...");
        System.out.println("Esperant connexions de clients...");
        System.out.println("Prem Ctrl+C per aturar el servidor\n");

        int clientNumber = 0;

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                clientNumber++;
                
                System.out.println("Client #" + clientNumber + " connectat des de: " + 
                                   socket.getInetAddress().getHostAddress());

                BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true);

                String linia;
                while ((linia = entrada.readLine()) != null) {
                    System.out.println("Client #" + clientNumber + " diu: " + linia);
                    
                    if (linia.equalsIgnoreCase("exit")) {
                        sortida.println("Adeu! Connexió tancada.");
                        System.out.println("Client #" + clientNumber + " ha tancat la connexió");
                        break;
                    }
                    
                    sortida.println("ECO: " + linia);
                }

                socket.close();
                System.out.println("Connexió amb client #" + clientNumber + " tancada\n");
                serverSocket.close();

            } catch (IOException e) {
                System.err.println("Error amb client #" + clientNumber + ": " + e.getMessage());
            }
        }
    }
}