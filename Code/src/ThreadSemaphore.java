/*
 * File: ThreadSemaphore.java
 * Incremento di un Contatore condiviso da più Thread tramite l'utilizzo di Semafori
 */

import java.util.concurrent.Semaphore;

public class ThreadSemaphore {
    public static void main(String[] args) {
        // Creo un'istanza della Classe di Supporto con il semaforo
        ThreadSemaphoreCounter tsc = new ThreadSemaphoreCounter();
        // Creo due Thread
        Thread t1 = new ThreadSemaphoreThread(1, tsc);
        Thread t2 = new ThreadSemaphoreThread(2, tsc);
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

class ThreadSemaphoreCounter {
    // Inizializzo una variabile contatore
    private int counter = 0;
    // Semaforo Mutualmente esclusivo Equo
    Semaphore fairMutex = new Semaphore(1, true);
    // Metodo esposto ai Thread
    protected void increaseAndPrintCounter(int threadNum) {
        try {
            fairMutex.acquire();
            counter++;
        } catch(InterruptedException ie) {
            System.out.println("Il Thread è stato interrotto durante l'acquisizione del Lock");
            ie.printStackTrace();
        } finally {
            fairMutex.release();
        }
        System.out.println("Il contatore vale " + this.counter + " ed e' stato aggiornato dal Thread " + threadNum);
    }
    // Getter del contatore
    protected int getCounter() {
        return this.counter;
    }
}

class ThreadSemaphoreThread extends Thread {
    // Identificatore del Thread
    int threadNum;
    // Oggetto della Classe di supporto
    ThreadSemaphoreCounter tsc;
    // Costruttore
    public ThreadSemaphoreThread(int threadNum, ThreadSemaphoreCounter tsc) {
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