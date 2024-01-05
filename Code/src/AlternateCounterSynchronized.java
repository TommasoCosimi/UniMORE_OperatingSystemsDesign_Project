/*
 * File: AlternateCounterSynchronized.java
 * Viene implementato un Contatore condiviso tra due Thread.
 * Il Thread 1 potra' aggiornare il contatore se e solo se questo e' dispari,
 * il Thread 2 solo se e' pari.
 */

public class AlternateCounterSynchronized {

    // Metodo main per l'avvio
    public static void main(String[] args) {
        new AlternateCounterSynchronized();
    }

    // Costruttore dell'oggetto principale
    private AlternateCounterSynchronized() {
        // Creo l'oggetto del counter da condividere
        AlternateCounterSynchronizedCounter acl = new AlternateCounterSynchronizedCounter();
        // Creo e avvio i due Thread
        AlternateCounterSynchronizedRunnable run1 = new AlternateCounterSynchronizedRunnable(1, acl);
        AlternateCounterSynchronizedRunnable run2 = new AlternateCounterSynchronizedRunnable(2, acl);
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

class AlternateCounterSynchronizedCounter {

    // Variabile contatore
    private int counter = 0;

    // Funzione per l'incremento del contatore
    protected synchronized void increaseAndPrintCounter(int threadNum) {
        // Finche' la condizione non viene rispettata, non incrementare
        while(threadNum%2 != this.counter%2) {
            try {
                System.out.println("Sono il Thread " + threadNum + " e sono bloccato in attesa della condizione");
                wait();
            } catch(InterruptedException ie) {
                System.out.println("Thread interrotto inaspettatamente");
                ie.printStackTrace();
            }
        }
        // Incrementa
        counter++;
        // Stampo il risultato dell'operazione
        System.out.println("Sono il Thread " + threadNum + " ed ho aggiornato il contatore: " + this.counter);
        // Segnala all'altro processo eventualmente bloccato
        notify();
    }

    // Metodo di supporto
    protected int getCounter() {
        return this.counter;
    }
}

class AlternateCounterSynchronizedRunnable implements Runnable {

    // Identificativo del Thread
    private int threadNum;
    // Risorsa condivisa
    private AlternateCounterSynchronizedCounter acl;

    // Costruttore
    protected AlternateCounterSynchronizedRunnable(int threadNum, AlternateCounterSynchronizedCounter acl) {
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
