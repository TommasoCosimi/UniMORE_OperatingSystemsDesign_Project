/*
 * File: AtomicVariable.java
 * Il Programma mira a mostrare le caratteristiche di
 * sincronizzazione delle Classi Atomiche in Java
 */

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariable {
    // Inizializzo un contatore atomico
    private AtomicInteger counter = new AtomicInteger(0);
    
    // Main
    public static void main(String[] args) {
        new AtomicVariable();
    }

    // Costruttore dell'oggetto principale
    private AtomicVariable() {
        // Creo due thread che incrementino il contatore in maniera concorrente
        Thread t1 = new AtomicVariableThread(1, counter);
        Thread t2 = new AtomicVariableThread(2, counter);
        t1.start();
        t2.start();
        // Attendo la terminazione e stampo il risultato
        try {
            t1.join();
            t2.join();
        } catch(InterruptedException ie) {
            System.out.println("Thread interrotto inaspettatamente");
        }
        System.out.println("Il valore finale del contatore e': " + this.counter.get());
    }
}

class AtomicVariableThread extends Thread {
    // Identificatore
    private int threadNum;
    // Risorsa condivisa
    private AtomicInteger counter;
    
    // Costruttore
    protected AtomicVariableThread(int threadNum, AtomicInteger counter) {
        this.threadNum = threadNum;
        this.counter = counter;
    }
    
    // Implementazione del Metodo run()
    @Override
    public void run() {
        for(int i=0; i<10; i++) {
            System.out.println("Sono il Thread " + this.threadNum +
                " ed il valore del contatore e': " + this.counter.incrementAndGet());
        }
    }
}