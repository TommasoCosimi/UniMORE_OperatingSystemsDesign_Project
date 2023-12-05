/*
 * File: ClassExtendThread.java
 * La classe mira a stampare sullo Standard Output l'identificativo del Thread assegnatogli dal Main. Estende la classe Thread.
 */

public class ClassExtendThread extends Thread {
    
    private int threadNumber;

    public ClassExtendThread(int tNum) {
        this.threadNumber = tNum;
    }

    @Override
    public void run() {
        System.out.println("Sono il Thread numero " + this.threadNumber + ".");
    }

}
