/*
 * File: ThreadPool.java
 * Implementazione di una Thread Pool per l'esecuzione di piu' task
 * utilizzando ExecutorService
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static final int NUMBER_OF_TASKS = 10;
    public static void main(String[] args) {
        // Inizializzo l'oggetto contatore
        ThreadPoolCounter tpc = new ThreadPoolCounter();
        // Inizializzo la Thread Pool
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i=0; i<NUMBER_OF_TASKS; i++) {
            // Sottometto alla Thread Pool i Task da eseguire
            executorService.execute(createTask(tpc));
        }
        // Chiudo la Thread Pool
        executorService.shutdown();
    }
    // Creatore di Task
    protected static Runnable createTask(ThreadPoolCounter tpc) {
        // Creo e ritorno un oggetto di Tipo Runnable che esegua l'operazione desiderata
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                tpc.increaseAndPrintCounter(Thread.currentThread().getName());
            }
        };
        return runnable;
    }
}

class ThreadPoolCounter {
    // Inizializzo una variabile contatore
    private int counter = 0;
    // Metodo esposto ai Thread
    protected synchronized void increaseAndPrintCounter(String threadName) {
        // Incremento il contatore
        counter++;
        // Stampo l'operazione e l'identificativo del Thread che l'ha effettuata
        System.out.println("Il contatore vale " + this.counter + " ed e' stato aggiornato dal Thread " + threadName);
    }
}