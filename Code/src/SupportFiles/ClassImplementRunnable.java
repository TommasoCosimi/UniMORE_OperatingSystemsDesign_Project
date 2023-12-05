/*
 * File: ClassImplementRunnable.java
 * La classe mira a stampare sullo Standard Output l'identificativo del Thread assegnatogli dal Main. Implementa l'interfaccia Runnable.
 */

public class ClassImplementRunnable implements Runnable {
    
    private int threadNumber;

    public ClassImplementRunnable(int tNum) {
        this.threadNumber = tNum;
    }

    @Override
    public void run() {
        System.out.println("Sono il Thread numero " + this.threadNumber + ".");
    }

}
