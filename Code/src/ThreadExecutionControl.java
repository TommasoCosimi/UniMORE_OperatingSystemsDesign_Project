/*
 * File: ThreadExecutionControl.java
 * Incremento di un Contatore condiviso da più Thread senza sincronizzazione
 */

public class ThreadExecutionControl {
    public static void main(String[] args) {
        // Creo un'istanza della Classe di Supporto
        ThreadExecutionControlCounter tecc = new ThreadExecutionControlCounter();
        // Creo due Thread
        Thread t1 = new ThreadExecutionControlThread(1, tecc);
        Thread t2 = new ThreadExecutionControlThread(2, tecc);
        // Avvio i due Thread
        t1.start();
        t2.start();
        // Attendo i due Thread e stampo il risultato
        try {
            t1.join();
            t2.join();
            System.out.println("Sono il Main, il valore finale del contatore e' " + tecc.getCounter());
        } catch(InterruptedException ie) {
            System.out.println("I Thread sono stati interrotti inaspettatamente");
            ie.printStackTrace();
        }
    }
}

class ThreadExecutionControlCounter {
    // Inizializzo una variabile contatore
    private int counter = 0;
    // Incremento il contatore
    protected void increaseAndPrintCounter(int threadNum) {
        counter++;
        try {
            Thread.sleep(100);
        } catch(InterruptedException ie) {
            System.out.println("Il Thread " + threadNum + " e' stato interrotto inaspettatamente");
            ie.printStackTrace();
        }
        System.out.println("Il contatore vale " + this.counter + " ed e' stato aggiornato dal Thread " + threadNum);
    }
    // Getter del contatore
    protected int getCounter() {
        return this.counter;
    }
}

class ThreadExecutionControlThread extends Thread {
    // Identificatore del Thread
    int threadNum;
    // Oggetto della Classe di supporto
    ThreadExecutionControlCounter tecc;
    // Costruttore
    public ThreadExecutionControlThread(int threadNum, ThreadExecutionControlCounter tecc) {
        this.threadNum = threadNum;
        this.tecc = tecc;
        System.out.println("Thread " + this.threadNum + " avviato");
    }
    // Implementazione del Metodo run
    @Override
    public void run() {
        for(int i=0; i<5; i++) {
            // Incremento il contatore
            this.tecc.increaseAndPrintCounter(threadNum);
            // Rilascio volontariamente la CPU
            Thread.yield();
        }
    }
}