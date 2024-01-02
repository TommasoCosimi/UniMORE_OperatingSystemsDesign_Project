/*
 * File: ThreadLock.java
 * Incremento di un Contatore condiviso da più Thread tramite l'utilizzo di Lock
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLock {
    public static void main(String[] args) {
        // Creo un'istanza della Classe di Supporto con il Lock
        ThreadLockCounter tlc = new ThreadLockCounter();
        // Creo due Thread
        Thread t1 = new ThreadLockThread(1, tlc);
        Thread t2 = new ThreadLockThread(2, tlc);
        // Avvio i due Thread
        t1.start();
        t2.start();
        // Attendo i due Thread e stampo il risultato
        try {
            t1.join();
            t2.join();
            System.out.println("Sono il Main, il valore finale del contatore e' " + tlc.getCounter());
        } catch(InterruptedException ie) {
            System.out.println("I Thread sono stati interrotti inaspettatamente");
            ie.printStackTrace();
        }
    }
}

class ThreadLockCounter {
    // Inizializzo una variabile contatore
    private int counter = 0;
    // Lock equo
    Lock fairLock = new ReentrantLock(true);
    // Metodo esposto ai Thread
    protected void increaseAndPrintCounter(int threadNum) {
        fairLock.lock();
        try {
            counter++;
            Thread.sleep(1000);
        } catch(InterruptedException ie) {
            System.out.println("Il Thread " + threadNum + " e' stato interrotto inaspettatamente");
            ie.printStackTrace();
        } finally {
            fairLock.unlock();
        }
        System.out.println("Il contatore vale " + this.counter + " ed e' stato aggiornato dal Thread " + threadNum);
    }
    // Getter del contatore
    protected int getCounter() {
        return this.counter;
    }
}

class ThreadLockThread extends Thread {
    // Identificatore del Thread
    int threadNum;
    // Oggetto della Classe di supporto
    ThreadLockCounter tlc;
    // Costruttore
    public ThreadLockThread(int threadNum, ThreadLockCounter tlc) {
        this.threadNum = threadNum;
        this.tlc = tlc;
        System.out.println("Thread " + this.threadNum + " avviato");
    }
    // Implementazione del Metodo run
    @Override
    public void run() {
        for(int i=0; i<5; i++) {
            // Incremento il contatore
            this.tlc.increaseAndPrintCounter(threadNum);
            // Rilascio volontariamente la CPU
            Thread.yield();
        }
    }
}