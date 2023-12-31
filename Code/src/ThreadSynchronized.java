/*
 * File: ThreadSynchronized.java
 * Incremento di un Contatore condiviso da più Thread tramite il costrutto Synchronized
 */

public class ThreadSynchronized {
    public static void main(String[] args) {
        // Creo un'istanza della Classe di Supporto con i metodi Synchronized
        ThreadSynchronizedCounter tsc = new ThreadSynchronizedCounter();
        // Creo due Thread
        Thread t1 = new ThreadSynchronizedThread(1, tsc);
        Thread t2 = new ThreadSynchronizedThread(2, tsc);
        // Avvio i due Thread
        t1.start();
        t2.start();
        // Attendo i due Thread e stampo il risultato
        try {
            t1.join();
            t2.join();
            System.out.println(tsc.getCounter());
        } catch(InterruptedException ie) {
            System.out.println("I Thread sono stati interrotti inaspettatamente");
            ie.printStackTrace();
        }
    }
}

class ThreadSynchronizedCounter {
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
    // Getter del contatore
    protected int getCounter() {
        return this.counter;
    }
}

class ThreadSynchronizedThread extends Thread {
    // Identificatore del Thread
    int threadNum;
    // Oggetto della Classe di supporto
    ThreadSynchronizedCounter tsc;
    // Costruttore
    public ThreadSynchronizedThread(int threadNum, ThreadSynchronizedCounter tsc) {
        this.threadNum = threadNum;
        this.tsc = tsc;
        System.out.println("Thread " + this.threadNum + " avviato");
    }
    // Implementazione del Metodo run
    @Override
    public void run() {
        for(int i=0; i<5; i++) {
            // Incremento il contatore
            this.tsc.increaseAndPrintCounter(threadNum);
            // Rilascio volontariamente la CPU
            Thread.yield();
        }
    }
}