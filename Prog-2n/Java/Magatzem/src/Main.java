import java.util.LinkedList;
import java.util.Queue;

// Classe que representarà un magatzem "virtual" compartit amb productes i consumidors
class Magatzem {
    private Queue<Integer> productes;
    // Aquesta serà la cua de productes
    private int capacitatMagatzem;
    // Aquesta serà la capacitat màxima del magatzem

    // Això és el constructor del magatzem, obviament, tindrà una capacitat limitada
    public Magatzem(int capacitat) {
        this.productes = new LinkedList<>();
        this.capacitatMagatzem = capacitat;
    }

    // Aquest mètode afegirà productes (amb un ID propi i un NOM de producte)
    public synchronized void afegirProducte(int idProducte, String nomProducte) {
        // En cas que el magatzem s'empleni fins al límit, el productor s'esperarà
        while (productes.size() >= capacitatMagatzem) {
            try {
                System.out.println(nomProducte + " espera (magatzem ple)");
                wait();
                // Aquesta funció anterior allibera el cadenat i espera
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Afegir productes quan encara hi hagi espai
        productes.add(idProducte);
        System.out.println(nomProducte + " ha produit el producte " + idProducte + "[" + "Magatzem: " + productes.size() + "/" + capacitatMagatzem + "]");
        notify();
        // Desperta un altre fil / thread
    }

    // Aquest mètode representa el consumidor, què fa? Doncs consumir producte (fentanil)
    public synchronized int consumirProducte(String nomConsumidor) {
        // En el cas poc probable que el magatzem estigui buit s'esperarà
        while (productes.isEmpty()) {
            try {
                System.out.println((nomConsumidor + " espera (magatzem buit)"));
                wait();
                // Aquesta és la funció que cridarà per esperar-se a tenir més producte
                // Com que el consumidor és addicte al producte estarà obligat a esperar-se a tenir-ne més quantitat.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // El consumidor obté producte i el consumeix
        // Pobret, sentirà una falsa sensació de satisfacció que el conduirà a necessitar més producte.
        int producte = productes.poll();
        System.out.println(nomConsumidor + " ha consumit el producte " + producte + "[" + productes.size() + "/" + capacitatMagatzem + "]");
        notify();
        // Desperta un fil productor
        return producte;
    }
}

// Classe productora, crea productes i els envia al magatzem
class Productor implements Runnable {
    private Magatzem magatzem;
    private String nom;
    private int numProducte; // El total de productes a produir
    private int contadorProductes = 0; //  Comptador intern
    private long tempsEsperaTotal = 0; // El temps total que es passa produint

    public Productor(Magatzem magatzem, String nomProducte, int numProducte) {
        this.magatzem = magatzem;
        this.nom = nomProducte;
        this.numProducte = numProducte;
    }

    @Override
    public void run() {
        // La producció es repeteix contínuament.
        for (int i = 0; i < numProducte; i++) {
            try {
                long inici = System.nanoTime(); // Temps abans de produir

                // Temps variable de producció, de 0 a 1000 ms s'escull un nombre i serà el temps de pausa
                // el qual serà relatiu, no sempre serà el mateix.
                Thread.sleep((int) (Math.random() * 1000));

                // Afegeix els productes al magatzem
                magatzem.afegirProducte(++contadorProductes, nom);
                long fi = System.nanoTime(); // Temps després de produir
                tempsEsperaTotal += (fi - inici);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ok " + nom + " ha acabat de produir bosses de fentanil");
    }

    // Getters per les estadístiques del magatzem (productes produïts i el temps d'espera)
    public int getProductesProduits() {
        return contadorProductes;
    }
    public long getTempsEspera() {
        return tempsEsperaTotal;
    }
}

// Classe consumidor: consumeix (extreu) els productes del magatzem
class Consumidor implements Runnable {
    private Magatzem magatzem;
    private String nom;
    private int numProductes;   // El total de productes que consumirà l'adicte.

    public Consumidor(Magatzem magatzem, String nomProducte, int numProductes) {
        this.magatzem = magatzem;
        this.nom = nomProducte;
        this.numProductes = numProductes;
    }

    @Override
    public void run() {
        // Consum de producte continu
        for (int i = 0; i < numProductes; i++) {
            try {
                Thread.sleep((int) (Math.random() * 1500)); // Alenteix el consum de producte entre 0 i 1500 ms
                magatzem.consumirProducte(nom);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ok " + nom + " ha acabat de consumir fentanil");
    }

    public int getProductesConsumits() {
        return numProductes;
    }
}

// La classe principal de tot el projecte, la resta eren getters/setters, classes i funcions
public class Main {
    public static void main(String[] args) {
        System.out.println("===== INICI DE LA FÀBRICA =====");

        // Es crea tot el magatzem amb una capacitat inicial de 5 productes
        Magatzem magatzem = new Magatzem(5);

        // Es creen els productors i consumidors (els quals fan gràcia perquè són pokémons)
        Productor productor1 = new Productor(magatzem, "Productor 1", 5);
        Productor productor2 = new Productor(magatzem, "Productor 2", 5);
        Productor productor3 = new Productor(magatzem, "Productor 3", 5);

        Consumidor consumidor1 = new Consumidor(magatzem, "Azelf", 3);
        Consumidor consumidor2 = new Consumidor(magatzem, "Mesprit", 5);
        Consumidor consumidor3 = new Consumidor(magatzem, "uxie", 5);

        // Creem els fils i els inicialitzem
        Thread t1 = new Thread(productor1);
        Thread t2 = new Thread(productor2);
        Thread t3 = new Thread(productor3);
        Thread t4 = new Thread(consumidor1);
        Thread t5 = new Thread(consumidor2);
        Thread t6 = new Thread(consumidor3);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        // Espera que els fils acabin de funcionar
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Resum final amb les estadístiques
        System.out.println("==== LA FÀBRICA HA TANCAT ====");

        System.out.println("\nEstadístiques Finals:");
        System.out.println("Productor 1 ha produït " + productor1.getProductesProduits() + " bosses de fentanil");
        System.out.println("Productor 2 ha produït " + productor2.getProductesProduits() + " bosses de fentanil");
        System.out.println("Productor 3 ha produït " + productor3.getProductesProduits() + " bosses de fentanil");

        System.out.println("Azelf ha consumit " + consumidor1.getProductesConsumits() + " fentanil");
        System.out.println("Mesprit ha consumit " + consumidor2.getProductesConsumits() + " fentanil");
        System.out.println("Uxie ha consumit " + consumidor3.getProductesConsumits() + " fentanil");

        // Calcula el temps mitjà d'espera entre tots els productors
        long tempsMitjaEsperaProductors = (
                productor1.getTempsEspera() +
                        productor2.getTempsEspera() +
                        productor3.getTempsEspera()
        ) / 3;
        System.out.println("\nTemps mitjà d'espera de cada productor: " + tempsMitjaEsperaProductors + " nanosegons");

        // Fem una segona simulació però amb un magatzem més gran
        System.out.println("\nCanviant la capacit" +
                "at del magatzem a 10...");
        magatzem = new Magatzem(10);
        // Repetim el cicle amb els mateixos productors i consumidors
        productor1 = new Productor(magatzem, "Productor 1", 5);
        productor2 = new Productor(magatzem, "Productor 2", 5);
        productor3 = new Productor(magatzem, "Productor 3", 5);
        consumidor1 = new Consumidor(magatzem, "Azelf", 3);
        consumidor2 = new Consumidor(magatzem, "Mesprit", 5);
        consumidor3 = new Consumidor(magatzem, "Uxie", 5);

        // Iniciem els nous fils amb nova capacitat
        t1 = new Thread(productor1);
        t2 = new Thread(productor2);
        t3 = new Thread(productor3);
        t4 = new Thread(consumidor1);
        t5 = new Thread(consumidor2);
        t6 = new Thread(consumidor3);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}
