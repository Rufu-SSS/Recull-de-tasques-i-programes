package FTP;
import java.io.*;
import java.net.*;
public class FTPServer {
    private static final int PORT = 2121;
    private static final String ROOT_DIR = "./server_files/";

    public static void main(String[] args) throws IOException {
        new File(ROOT_DIR).mkdirs();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor FTP iniciat al port " + PORT);
            System.out.println("Directori: " + new File(ROOT_DIR).getAbsolutePath());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connectat: " + clientSocket.getInetAddress());
                new Thread(() -> handleClient(clientSocket)).start();
    } } }
    private static void handleClient(Socket socket) {
        try (
            socket;
            DataInputStream in = new DataInputStream(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("220 Benvingut al servidor FTP en Java!");

            String line;
            while ((line = readLine(in)) != null && !line.isEmpty()) {
                String[] parts = line.trim().split(" ", 3);
                String command = parts[0].toUpperCase();
                System.out.println("Comandament rebut: " + line);
                switch (command) {
                    case "USER" : {
                        out.println("331 Usuari acceptat.");
                        break;
                    }
                    case "PASS" : {
                        if (parts.length > 1 && "1234".equals(parts[1]))
                            out.println("230 Sessio iniciada correctament.");
                        else
                            out.println("530 Contrasenya incorrecta.");
                        break;
                    }
                    case "LIST" : {
                        File dir = new File(ROOT_DIR);
                        File[] files = dir.listFiles();
                        StringBuilder sb = new StringBuilder("150 Llista de fitxers:\n");
                        if (files != null && files.length > 0)
                            for (File f : files)
                                sb.append(f.isDirectory() ? "[DIR] " : "[FIT] ")
                                    .append(f.getName())
                                    .append(" (").append(f.length()).append(" bytes)\n");
                        else
                            sb.append("(directori buit)\n");
                        out.println(sb);
                        out.println("226 Llista completada.");
                        break;
                    }
                    case "STOR" : {
                        if (parts.length < 3) {
                            out.println("501 Sintaxi incorrecta. Us: STOR <nom> <mida>");
                            break;
                        }
                        try {
                            String filename = new File(parts[1]).getName();
                            long fileSize = Long.parseLong(parts[2]);
                            receiveFile(in, out, filename, fileSize);
                        } catch (NumberFormatException e) {
                            out.println("501 Mida de fitxer invalida.");
                        }
                        break;
                    }
                    case "RETR" : {
                        if (parts.length < 2) {
                            out.println("501 Sintaxi incorrecta. Us: RETR <nom>");
                            break;
                        }
                        sendFile(socket, out, new File(parts[1]).getName());
                        break;
                    }
                    case "DELE" : {
                        if (parts.length < 2) {
                            out.println("501 Sintaxi incorrecta. Us: DELE <nom>");
                            break;
                        }
                        File f = new File(ROOT_DIR + new File(parts[1]).getName());
                        out.println(f.delete() ? "250 Fitxer eliminat." : "550 Error eliminant fitxer.");
                        break;
                    }
                    case "QUIT" : {
                        out.println("221 Fins aviat!");
                        return;
                    }
                    default : {
                        out.println("502 Comandament desconegut: " + command);
                        break;
        } } } } catch (IOException e) {
            System.err.println("Error al client: " + e.getMessage());
    } }

    private static void receiveFile(DataInputStream in, PrintWriter out,
            String filename, long fileSize) {
        out.println("125 Preparat per rebre: " + filename);
        try (FileOutputStream fos = new FileOutputStream(ROOT_DIR + filename)) {
            byte[] buffer = new byte[4096];
            long remaining = fileSize;
            while (remaining > 0) {
                int toRead = (int) Math.min(buffer.length, remaining);
                int bytesRead = in.read(buffer, 0, toRead);
                if (bytesRead == -1) break;
                fos.write(buffer, 0, bytesRead);
                remaining -= bytesRead;
            }
            out.println("226 Fitxer rebut: " + filename);
        } catch (IOException e) {
            out.println("426 Error en rebre el fitxer.");
    } }

    private static void sendFile(Socket socket, PrintWriter out, String filename) {
        File file = new File(ROOT_DIR + filename);
        if (!file.exists()) {
            out.println("550 Fitxer no trobat.");
            return;
        }
        try {
            OutputStream os = socket.getOutputStream();
            out.println("150 Enviant " + file.length() + " bytes");
            out.flush();
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1)
                    os.write(buffer, 0, bytesRead);
                os.flush();
            }
            out.println("226 Transferencia completada.");
        } catch (IOException e) {
            out.println("426 Error en enviar el fitxer.");
    } }

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