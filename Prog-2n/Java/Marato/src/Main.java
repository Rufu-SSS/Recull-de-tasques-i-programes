//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

class Corredor extends Thread {
    private String nom;
    private int velocitat;
    public Corredor(String nom, int velocitat) {
        this.nom = nom;
        this.velocitat = velocitat;
        setName(nom);
    }
    //Declarem dues variables i una funció amb un nom i una velocitat
        //Public Corredor(...) crea un corredor en una cursa virtual

    @Override
    public void run() {
        System.out.println(nom + " comença la cursa!");
        for(int km=0; km<=42; km++){
            try{
                sleep(velocitat);
                System.out.println(nom + " ha completat " + km + " km");
            }catch(InterruptedException e){
                System.out.println(nom + " ha estat interromput");
                return;
            }
        }
        System.out.println("Felicitats " + nom + " has acabat la cursa!");
    }
    //Creem una funció per a inicialitzar i anar dient els km correguts de la marató
        //Dins d'aquesta funció augmentarem el valor de la variable int km un per un
        //farem sortir per la pantalla cada vegada que augmenti la variable km
}


public class Main {
    public static void main(String[] args) {

        System.out.printf("== INICI DE LA CURSA ==");
        Corredor corredor1 = new Corredor("Atraxa", 200);
        Corredor corredor2 = new Corredor("Vorniclex", 200);
        Corredor corredor3 = new Corredor("Sheoldred", 200);
        Corredor corredor4 = new Corredor("Jin Gitaxias", 200);
        Corredor corredor5 = new Corredor("Ebon", 200);
        corredor1.start();
        corredor2.start();
        corredor3.start();
        corredor4.start();
        corredor5.start();
        try{
            corredor1.join();
            corredor2.join();
            corredor3.join();
            corredor4.join();
            corredor5.join();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
        System.out.printf("== FINAL DE LA CURSA ==");
    }
    //Dins la funció main hi farem servir la funció de la classe Corredor
        //Afegirem nous membres a la classe Corredor amb els seus propis noms i velocitats
        //Iniciarem els dos corredors i cridarem les funcions de la classe Corredor

}