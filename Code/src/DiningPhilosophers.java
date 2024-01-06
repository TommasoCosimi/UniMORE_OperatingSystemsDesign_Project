/*
 * File: DiningPhilosophers.java
 * Il Programma mira ad implementare una soluzione del classico problema
 * della cena tra i Filosofi tramite l'utilizzo delle metodologie
 * di sincronizzazione messe a disposizione dal Linguaggio Java
 */

import java.util.Random;
import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    
    // Array di semafori per l'acquisizione delle bacchette
    private Semaphore[] sticksSemaphores = new Semaphore[5];

    public static void main(String[] args) {
        new DiningPhilosophers();
    }

    private DiningPhilosophers() {
        // Inizializzo i Semafori come mutualmente esclusivi ed equi
        for(int i=0; i<5; i++) {
            sticksSemaphores[i] = new Semaphore(1, true);
        }

        // Creo ed avvio i Filosofi
        for(int i=0; i<5; i++) {
            DiningPhilosophersPhilosopher philosopherRunnable =
                new DiningPhilosophersPhilosopher(i+1, sticksSemaphores);
            Thread philosopherThread = new Thread(philosopherRunnable);
            philosopherThread.start();
        }
    }
}

class DiningPhilosophersPhilosopher implements Runnable {

    // Identificativo del filosofo
    private int philosopherNum;
    // Semafori per l'acquisizione delle bacchette
    private Semaphore[] stickSemaphores;

    // Costruttore
    protected DiningPhilosophersPhilosopher(int philosopherNum, Semaphore[] sticksSemaphores) {
        this.philosopherNum = philosopherNum;
        this.stickSemaphores = sticksSemaphores;
        System.out.println("Filosofo numero " + this.philosopherNum + " creato");
    }

    // Metodo per far pensare il filosofo 
    // aggiungo come parametro se sta pensando perche' non ha mangiato per
    // farlo mangiare un numero fissato di volte
    private void think(boolean becauseDidntEat) {
        // Scelgo un tempo per pensare compreso tra 200 e 1000ms
        int thinkTime = new Random(System.currentTimeMillis()).nextInt(200, 1001);
        System.out.println("Il filosofo numero " + this.philosopherNum +
            " pensera' per " + thinkTime/1000.0 + " secondi");
        try {
            // Simulo il pensiero
            Thread.sleep(thinkTime);
        } catch(InterruptedException ie) {
            System.out.println("Il Thread e' stato interrotto inaspettatamente");
            ie.printStackTrace();
        }
        System.out.println("Il filosofo numero " + this.philosopherNum +
            " ha terminato di pensare");
        // Se sono entrato in think() perche' non ho mangiato, torno su eat()
        if(becauseDidntEat) {
            eat();
        }
    }

    // Metodo per far mangiare il filosofo
    // Il controllo della disponibilita' delle bacchette va effettuato in una sezione critica
    private synchronized void eat() {
        System.out.println("Il filosofo numero " + this.philosopherNum +
            " tenta di acquisire le bacchette");
        // Controllo se entrambe le bacchette sono disponibili
        if(this.stickSemaphores[(philosopherNum-1)%5].availablePermits() == 1
            && this.stickSemaphores[(philosopherNum)%5].availablePermits() == 1) {
            try {
                // Acquisizione Bacchetta sinistra
                this.stickSemaphores[(philosopherNum-1)%5].acquire();
                // Acquisizione Bacchetta destra
                this.stickSemaphores[(philosopherNum)%5].acquire();
                // Mangia
                System.out.println("Il filosofo " + this.philosopherNum + " sta mangiando");
                Thread.sleep(100);
            } catch(InterruptedException ie) {
                System.out.println("Il Thread e' stato interrotto inaspettatamente");
                ie.printStackTrace();
            } finally {
                // Rilascio Bacchetta sinistra
                this.stickSemaphores[(philosopherNum-1)%5].release();
                // Rilascio Bacchetta destra
                this.stickSemaphores[(philosopherNum)%5].release();
                // Termina di mangiare
                System.out.println("Il filosofo " + this.philosopherNum + " ha terminato di mangiare");
            }
        } else {
            System.out.println("Il filosofo numero " + this.philosopherNum +
            " non e' riuscito nell'acquisizione delle bacchette, pensera' e tornera' per mangiare");
            // Chiamo think() con l'argomento true
            think(true);
        }
    }

    // Implementazione di run
    @Override
    public void run() {
        System.out.println("Filosofo numero " + this.philosopherNum + " avviato");
        for(int i=0; i<3; i++) {
            eat();
            think(false);
        }
    }
}