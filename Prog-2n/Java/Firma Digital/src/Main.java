import java.security.*;
import java.util.Base64;

public class Main {

    public static void main(String[] args) throws Exception {

        // 1. Generar claus RSA
        KeyPair pair = generarClaus();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        System.out.println("Claus generades correctament.");

        // Missatge original
        String missatge = "¿Estás seguro? ¡SAL MARINA! ¿DÓNDE ESTÁ OMNIMAN? ¿Cómo es posible eso? No quiero lastimarte, señor. ¡TE NECESITO, SAL MARINA!";

        // 2. Signar missatge
        byte[] firma = signar(missatge, privateKey);

        System.out.println("Missatge: " + missatge);
        System.out.println("Firma (Base64): " +
                Base64.getEncoder().encodeToString(firma));

        // 3. Validació correcta
        boolean validaCorrecte = verificar(missatge, firma, publicKey);
        System.out.println("Firma vàlida (cas correcte)? " + validaCorrecte);

        // 4. Cas incorrecte (missatge modificat)
        String missatgeManipulat = "Hola, això és un missatge MODIFICAT";
        boolean validaIncorrecte = verificar(missatgeManipulat, firma, publicKey);
        System.out.println("Firma vàlida (cas incorrecte)? " + validaIncorrecte);
    }

    // Generar parell de claus RSA
    public static KeyPair generarClaus() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    // Signar missatge
    public static byte[] signar(String dades, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(dades.getBytes());
        return signature.sign();
    }

    // Verificar signatura
    public static boolean verificar(String dades, byte[] firma, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(dades.getBytes());
        return signature.verify(firma);
    }
}