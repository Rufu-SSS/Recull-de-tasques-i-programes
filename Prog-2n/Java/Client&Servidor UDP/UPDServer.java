import java.net.*;

public class UPDServer {
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket(5555);
        System.out.println("Servidor UDP actiu i escoltant! (port 5555)");
        
        byte[] buffer = new byte[1024];
        
        for(int i=0; i<3; i++){
        DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
        socket.receive(paquet);
        String missatge = new String(paquet.getData(), 0, paquet.getLength());
        InetAddress adreca = paquet.getAddress();
        int port=paquet.getPort();

        System.out.println("Missatge " + (i+1) + " rebut de " + adreca + ":" + port);
                System.out.println("Contingut: " + missatge);
                System.out.println("---");
        }
        System.out.println("Servidor finalitzat després de rebre 3 missatges");


        socket.close();
    }
}