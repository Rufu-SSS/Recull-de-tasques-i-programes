// Classe Main que implementa Runnable amb la finalitat de crear objectes que
// es poden executar en Threads
public class Main implements Runnable {

    String strImprimir;
    // Aquest és el text que imprimirà cada fil

    // Aquest constructor llegeix el string anterior i el prepara per imprimir
    public Main(String strP){
        strImprimir = strP;
    }

    @Override
    public void run(){
        // Tots els fils imprimiran 5 vegades el seu text
        for(int i = 0; i < 5; i++){
            System.out.println(strImprimir + " " + i);
        }
    }

    public static void main(String[] args) {
        // Com a missatge inicial fa una pregunta amb argot del joc MTG
        // (magic the gathering), Rhystic Study és una carta de color blau on
        // per 2 mana incolor i 1 mana blau baixes un enchant el qual obliga els
        // oponents a pensar si val la pena pagar 1 manà incolor de la seva mana pool
        // o deixar que el propietari tingui una carta extra.
        System.out.println("Que passa cada torn quan algú baixa un Rhystic Study:");

        // Creem els dos objectes Runnable de textos diferents
        Runnable primerRunnable = new Main("Pay the ①");
        Runnable segonRunnable = new Main("I draw a card");

        // Creem els fils associats als fils anteriors Runnable
        Thread primer=new Thread(primerRunnable);
        Thread segon=new Thread(segonRunnable);

        // Començen els dos fils
        primer.start();
        segon.start();

        // En acabar el fil avisa per la consola
        System.out.println("Fil principal acabat");
    }
}
