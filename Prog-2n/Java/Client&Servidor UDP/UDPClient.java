import java.net.*;

public class UDPClient {  
    public static void main(String[] args) {
        DatagramSocket socket = null;
        
        try {
            socket = new DatagramSocket();

            String[] missatges = {
                "Can we get",
                "Much higher,",
                "So higher, oh oh oh"
            };
            
            InetAddress adreca = InetAddress.getByName("127.0.0.1");
            
            for(int i = 0; i < missatges.length; i++) {
                byte[] dades = missatges[i].getBytes();
                
                DatagramPacket paquet = new DatagramPacket(dades, dades.length, adreca, 5555);
                
                socket.send(paquet);
                System.out.println("Missatge " + (i+1) + " enviat: " + missatges[i]);
                
                Thread.sleep(750);
            }
            
            System.out.println("Tots els paquets enviats correctament!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Socket tancat");
            }
        }
    }
}