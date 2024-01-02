/*
 * File: ImplementRunnable.java
 * Programma che generi un numero fisso di Processi Leggeri implementando l'Interfaccia Runnable
 */

public class ImplementRunnable {
    // Numero di processi Leggeri da generare
    private static final int NUM_OF_THREADS = 5;
    public static void main(String[] args) {
        System.out.println("Sono il Main e sto per creare i Thread.");        
        // Creo NUM_OF_THREADS Thread e li eseguo
        for(int i=0; i<NUM_OF_THREADS; i++) {
            ImplementRunnableRunnable irr = new ImplementRunnableRunnable(i);
            Thread t = new Thread(irr);
            t.start();
        }
        System.out.println("Sono il Main ed ho terminato l'esecuzione.");
    }
}

class ImplementRunnableRunnable implements Runnable {
    // Identificatore del Thread
    private int threadNumber;
    // Costruttore
    public ImplementRunnableRunnable(int tNum) {
        this.threadNumber = tNum;
    }
    // Implementazione del Metodo run()
    @Override
    public void run() {
        System.out.println("Sono il Thread numero " + this.threadNumber + ".");
    }
}