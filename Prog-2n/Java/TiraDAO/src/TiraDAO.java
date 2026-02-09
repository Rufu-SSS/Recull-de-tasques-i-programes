
class TiradaDau{
    private int tiradaDau;
    public TiradaDau(int e){ tiradaDau=e;}
    public synchronized int getSumaTirada(){return tiradaDau;}
    public synchronized void setSumaTirada(int e){tiradaDau+=e;}
}

public class TiraDAO implements Runnable{
    private TiradaDau xobj;
    public TiraDAO(TiradaDau m){xobj=m;}
    public void run() {
        try {
        Thread.sleep(1000);
        int resultatDau = (int) (Math.random() * 20) + 1;
        xobj.setSumaTirada(resultatDau);
        System.out.println("Tirada fil "+Thread.currentThread().getName()+": "+resultatDau);
    }
        catch (InterruptedException e) {
    }
}
    public static void main(String[] args) throws InterruptedException{
        TiradaDau ans=new TiradaDau(0);
        System.out.println("RUN:");
        TiraDAO obj1=new TiraDAO(ans);
        TiraDAO obj2=new TiraDAO(ans);
        TiraDAO obj3=new TiraDAO(ans);
        Thread fil_1=new Thread(obj1);
        fil_1.setName("Primer D20");
        Thread fil_2 = new Thread(obj2);
        fil_2.setName("Segon D20");
        Thread fil_3=new Thread(obj3);
        fil_3.setName("Tercer D20");
        fil_1.start();
        fil_2.start();
        fil_3.start();
        fil_1.join();
        fil_2.join();
        fil_3.join();
        System.out.println("Total tirada: "+ans.getSumaTirada());
        System.out.println("Final fil principal");

    }
}