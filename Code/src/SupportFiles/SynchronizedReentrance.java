public class SynchronizedReentrance {

    public static void main(String[] args) {

        // Creo un'istanza della Classe di Supporto con i metodi Synchronized
        SynchronizedReentranceSupportClass sc = new SynchronizedReentranceSupportClass();

        // Creo due Thread
        Thread t1 = new Thread(() -> {
            for(int i=0; i<5; i++) {
                sc.increaseAndPrintCounter(1);
            }
        });
        Thread t2 = new Thread(() -> {
            for(int i=0; i<5; i++) {
                sc.increaseAndPrintCounter(2);
            }
        });

        // Avvio i due Thread
        t1.start();
        t2.start();
    }
}

class SynchronizedReentranceSupportClass {

    // Inizializzo una variabile contatore
    private int counter = 0;

    // Il metodo verrà raggiunto tramite l'altro esposto
    private synchronized void increaseCounter() {
        counter++;
    }

    // Metodo esposto ai Thread
    protected synchronized void increaseAndPrintCounter(int threadNum) {
        this.increaseCounter();
        System.out.println("Il contatore vale " + this.counter + " ed e' stato aggiornato dal Thread " + threadNum);
    }
}