/*
 * File: SequentialProcess.java
 * Implementazione della somma tra due interi per l'esemplificazione di un processo sequenziale.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SequentialProcess {
    public static void main(String[] args) {
        String a = "";
        String b = "";
        int c;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Inserisci un numero: ");
            a = reader.readLine();
            System.out.print("Inserisci un altro numero: ");
            b = reader.readLine();
        } catch(IOException ioe) {
            System.out.println("Errore nella lettura dallo Standard Input!");
            ioe.printStackTrace();
        } finally {
            if(a.equals("") || b.equals("")) {
                System.exit(0);
            }
            // Suppongo che l'utente abbia inserito Numeri Interi
            c = Integer.parseInt(a) + Integer.parseInt(b);
            System.out.println("La somma tra i due numeri inseriti e': " + c);
        }
    }
}
