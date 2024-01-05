/*
 * File: AlternateCounterLock.java
 * Viene implementato un Contatore condiviso tra due Thread.
 * Il Thread 1 potra' aggiornare il contatore se e solo se questo e' dispari,
 * il Thread 2 solo se e' pari.
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AlternateCounterLock {
    
    // Metodo main per l'avvio
    public static void main(String[] args) {
        new AlternateCounterLock();
    }

    // Costruttore dell'oggetto principale
    private AlternateCounterLock() {
        // Creo l'oggetto del counter da condividere
        AlternateCounterLockCounter acl = new AlternateCounterLockCounter();
        // Creo e avvio i due Thread
        AlternateCounterLockRunnable run1 = new AlternateCounterLockRunnable(1, acl);
        AlternateCounterLockRunnable run2 = new AlternateCounterLockRunnable(2, acl);
        Thread t1 = new Thread(run1);
        Thread t2 = new Thread(run2);
        t1.start();
        t2.start();
        // Attendo la loro terminazione e stampo il risultato
        try {
            t1.join();
            t2.join();
        } catch(InterruptedException ie) {
            System.out.println("Errore nell'attesa dei Thread figli");
            ie.printStackTrace();
        }
        System.out.println("Il valore finale del contatore e': "
            + acl.getCounter());
    }
}

class AlternateCounterLockCounter {

    // Variabile contatore
    private int counter = 0;
    // Lock per l'accesso in mutua esclusione alla sezione critica
    // non equo per aumentare la possibilita' di bloccarsi sulla condizione
    private Lock lck = new ReentrantLock(false);
    // Variabile condizione
    private Condition parity = lck.newCondition();

    // Funzione per l'incremento del contatore
    protected void increaseAndPrintCounter(int threadNum) {
        // Acquisizione del Lock
        lck.lock();
        try {
            // Finche' la condizione non viene rispettata, non incrementare
            while(threadNum%2 != this.counter%2) {
                System.out.println("Sono il Thread " + threadNum +
                    " e sono bloccato sulla variabile condizione");
                parity.await();
            }
            // Incrementa
            counter++;
            // Stampo il risultato dell'operazione
            System.out.println("Sono il Thread " + threadNum +
                " ed ho aggiornato il contatore: " + this.counter);
            // Segnala all'altro processo eventualmente bloccato
            parity.signal();
        } catch(InterruptedException ie) {
            System.out.println("Thread interrotto inaspettatamente");
            ie.printStackTrace();
        } finally {
            // Rilascio del Lock
            lck.unlock();
        }
    }

    // Metodo di supporto
    protected int getCounter() {
        return this.counter;
    }
}

class AlternateCounterLockRunnable implements Runnable {

    // Identificativo del Thread
    private int threadNum;
    // Risorsa condivisa
    private AlternateCounterLockCounter acl;

    // Costruttore
    protected AlternateCounterLockRunnable(int threadNum, AlternateCounterLockCounter acl) {
        this.threadNum = threadNum;
        this.acl = acl;
        System.out.println("Runnable " + this.threadNum + " creato");
    }

    // Implementazione del Metodo run()
    @Override
    public void run() {
        System.out.println("Thread " + this.threadNum + " avviato");
        for(int i=0; i<10; i++) {
            acl.increaseAndPrintCounter(this.threadNum);
        }
    }
}
