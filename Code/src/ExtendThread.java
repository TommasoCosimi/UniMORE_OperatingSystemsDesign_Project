/*
 * File: ExtendThread.java
 * Programma che generi un numero fisso di Processi Leggeri estendendo la classe Thread
 */

public class ExtendThread {
    // Numero di processi Leggeri da generare
    private static final int NUM_OF_THREADS = 5;
    public static void main(String[] args) {
        System.out.println("Sono il Main e sto per creare i Thread.");
        // Creo NUM_OF_THREADS Thread e li eseguo
        for(int i=0; i<NUM_OF_THREADS; i++) {
            ExtendThreadThread ett = new ExtendThreadThread(i);
            ett.start();
        }
        System.out.println("Sono il Main ed ho terminato l'esecuzione.");
    }
}

class ExtendThreadThread extends Thread {
    // Identificatore del Thread
    private int threadNumber;
    public ExtendThreadThread(int tNum) {
        this.threadNumber = tNum;
    }
    // Implementazione del Metodo run()
    @Override
    public void run() {
        System.out.println("Sono il Thread numero " + this.threadNumber + ".");
    }
}