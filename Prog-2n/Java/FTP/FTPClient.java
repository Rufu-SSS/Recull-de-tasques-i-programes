package FTP;
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class FTPClient {
    private static final String HOST = "localhost";
    private static final int PORT = 2121;
    public static void main(String[] args) {
        System.out.println("Connectant al servidor FTP " + HOST + ":" + PORT);
        try (
            Socket socket = new Socket(HOST, PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Servidor: " + readLine(in));

            System.out.print("Usuari: ");
            out.println("USER " + scanner.nextLine());
            System.out.println("Servidor: " + readLine(in));
            System.out.print("Contrasenya: ");
            out.println("PASS " + scanner.nextLine());
            System.out.println("Servidor: " + readLine(in));
            System.out.println("\nComandaments disponibles:");
            System.out.println("  LIST          - Llistar fitxers");
            System.out.println("  STOR <fitxer> - Pujar fitxer");
            System.out.println("  RETR <fitxer> - Descarregar fitxer");
            System.out.println("  DELE <fitxer> - Eliminar fitxer");
            System.out.println("  QUIT          - Sortir\n");
            while (true) {
                System.out.print("ftp> ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;
                String[] parts = input.split(" ", 2);
                String cmd = parts[0].toUpperCase();
                String arg = parts.length > 1 ? parts[1] : "";
                switch (cmd) {
                    case "STOR" : {
                        if (arg.isEmpty()) {
                            System.out.println("Us: STOR <nom_fitxer>");
                        } else {
                            try {
                                uploadFile(socket, out, in, arg);
                            } catch (IOException e) {
                                System.out.println("Error pujant fitxer: " + e.getMessage());
                            }
                        }
                        break;
                    }
                    case "RETR" : {
                        if (arg.isEmpty()) {
                            System.out.println("Us: RETR <nom_fitxer>");
                        } else {
                            try {
                                downloadFile(socket, out, in, arg);
                            } catch (IOException e) {
                                System.out.println("Error descarregant fitxer: " + e.getMessage());
                            }
                        }
                        break;
                    }
                    default : {
                        out.println(input);
                        String resp;
                        while ((resp = readLine(in)) != null && !resp.isEmpty()) {
                            System.out.println("Servidor: " + resp);
                            if (resp.matches("^[245]\\d{2}.*")) break;
                        }
                        if (cmd.equals("QUIT")) return;
                        break;
        } } } } catch (IOException e) {
            System.err.println("Error de connexio: " + e.getMessage());
    } }
    private static void uploadFile(Socket socket, PrintWriter out,
            DataInputStream in, String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Fitxer local no trobat: " + filename);
            return;
        }
        out.println("STOR " + filename + " " + file.length());
        System.out.println("Servidor: " + readLine(in));

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            OutputStream os = socket.getOutputStream();
            while ((bytesRead = fis.read(buffer)) != -1)
                os.write(buffer, 0, bytesRead);
            os.flush();
        }
        System.out.println("Servidor: " + readLine(in));
        System.out.println("Fitxer pujat: " + filename);
    }
    private static void downloadFile(Socket socket, PrintWriter out,
            DataInputStream in, String filename) throws IOException {
        out.println("RETR " + filename);
        String resp = readLine(in);
        System.out.println("Servidor: " + resp);
        if (resp == null || !resp.startsWith("150")) return;
        String[] tokens = resp.split(" ");
        long fileSize = Long.parseLong(tokens[1]);
        try (FileOutputStream fos = new FileOutputStream("descarrega_" + filename)) {
            byte[] buffer = new byte[4096];
            long remaining = fileSize;
            while (remaining > 0) {
                int toRead = (int) Math.min(buffer.length, remaining);
                int bytesRead = socket.getInputStream().read(buffer, 0, toRead);
                if (bytesRead == -1) break;
                fos.write(buffer, 0, bytesRead);
                remaining -= bytesRead;
        } }
        System.out.println("Servidor: " + readLine(in));
        System.out.println("Desat com: descarrega_" + filename);
    }
    private static String readLine(DataInputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = in.read()) != -1) {
            if (c == '\n') break;
            if (c != '\r') sb.append((char) c);
        }
        if (c == -1 && sb.length() == 0) return null;
        return sb.toString();
    } }