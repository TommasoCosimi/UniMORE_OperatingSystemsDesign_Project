/*
 * File: MainExtendThread.java
 * La classe crea un numero NUM_OF_THREADS di Thread che estendano la classe Thread.
 */

public class MainExtendThread {

    private static final int NUM_OF_THREADS = 5;

    public static void main(String[] args) {

        System.out.println("Sono il Main e sto per creare i Thread.");        
        
        for(int i=0; i<NUM_OF_THREADS; i++) {
            ClassExtendThread cet = new ClassExtendThread(i);
            cet.start();
        }
        
        System.out.println("Sono il Main ed ho terminato l'esecuzione.");

    }

}