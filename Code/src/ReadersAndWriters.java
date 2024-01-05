/*
 * File: ReadersAndWriters.java
 * Il Programma mira ad implementare una soluzione del classico problema
 * dei Processi Lettori e Scrittori tramite l'utilizzo delle metodologie
 * di sincronizzazione messe a disposizione dal Linguaggio Java
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadersAndWriters {
    // Lock rientrante di lettura e scrittura equo
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock(true);
    // Singoli Lock rientranti di lettura e scrittura
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
    // Lock per la stampa mutualmente esclusiva sullo standard output (equo)
    private Lock printLock = new ReentrantLock(true);
    // File da leggere e scrivere
    private File file;
    // Quantita' di lettori e scrittori
    private int numOfReaders = 0;
    private int numOfWriters = 0;

    public static void main(String[] args) {
        /*
         * Controllo se l'utente ha passato il numero di Processi Lettori
         * e scrittori come parametro
         */
        if(args.length != 2) {
            System.out.println("Utilizzo: java ReadersAndWriters numReaders numWriters");
            System.exit(-1);
        } else if(Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[1]) <= 0) {
            // Controllo che i parametri passati siano > 0
            System.out.println("Inserire un numero di Lettori e Scrittori adeguato!");
            System.exit(-2);
        }
        // Creo un oggetto che rappresenti l'istanza del mio programma
        ReadersAndWriters rw =
            new ReadersAndWriters(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        // Avvio l'esecuzione
        rw.createAndStartReaders();
        rw.createAndStartWriters();
    }

    // Costruttore dell'oggetto principale
    private ReadersAndWriters(int numOfReaders, int numOfWriters) {
        // Inizializzo la variabile File
        this.file = new File("File.txt");
        // Controllo la sua esistenza prima di continuare
        if(!this.file.exists()) {
            System.out.println("Il file di supporto non esiste. Il programma terminera'");
            System.exit(-3);
        }
        // Inizializzo il numero di lettori e scrittori
        this.numOfReaders = numOfReaders;
        this.numOfWriters = numOfWriters;
    }

    // Inizializzazione dei lettori
    private void createAndStartReaders() {
        for(int i=0; i<this.numOfReaders; i++) {
            ReadersAndWritersReader readerRunnable =
                new ReadersAndWritersReader((i+1), this.file, this.readLock, this.printLock);
            Thread readerThread = new Thread(readerRunnable);
            readerThread.start();
        }
    }

    // Inizializzazione degli scrittori
    private void createAndStartWriters() {
        for(int i=0; i<this.numOfWriters; i++) {
            ReadersAndWritersWriter writerRunnable =
                new ReadersAndWritersWriter((i+1), this.file, this.writeLock);
            Thread writerThread = new Thread(writerRunnable);
            writerThread.start();
        }
    }
}

// Lettori
class ReadersAndWritersReader implements Runnable {
    // Identificativo del lettore
    private int numOfReader;
    // Risorsa condivisa
    private File file;
    // Lock di lettura (Mutualmente esclusivo tra Lettori e Scrittori, non tra Lettori)
    private Lock readLock;
    // Lock aggiuntivo per la mutua esclusione in stampa (rende piu' leggibile l'output)
    private Lock printLock;

    // Costruttore
    protected ReadersAndWritersReader(int numOfReader, File sharedResource, Lock readLock, Lock printLock) {
        this.numOfReader = numOfReader;
        this.file = sharedResource;
        this.readLock = readLock;
        this.printLock = printLock;
        System.out.println("Processo lettore numero " + numOfReader + " creato");
    }

    // Routine di Lettura
    private void read() {
        // Acquisizione del Lock di lettura
        readLock.lock();
        System.out.println("L" + this.numOfReader + ": Sono il Processo lettore numero " + this.numOfReader +
                " ed ho acquisito il file");
        try {
            // Acquisizione del Lock per la stampa sullo standard output
            this.printLock.lock();
            try {
                printFileContent();
            } finally {
                // Rilascio del Lock per la stampa sullo standard output
                this.printLock.unlock();
            }
        } finally {
            System.out.println("L" + this.numOfReader + ": Sono il Processo lettore numero " + this.numOfReader +
            " e sto rilasciando il file");
            // Rilascio del Lock di lettura
            readLock.unlock();
        }
    }

    // Lettura e stampa del contenuto del file
    private void printFileContent() {
        try {
            System.out.println("L" + this.numOfReader + ": Sono il Processo lettore numero " + this.numOfReader +
                " ed il contenuto del file e':");
            // BufferedReader per la lettura
            BufferedReader buffRead = new BufferedReader(new FileReader(this.file));
            // Stringa di supporto per salvare il contenuto di una riga del file
            String supportVar;
            // Fin tanto che vi sono righe non nulle nel file, stampare la riga
            while((supportVar = buffRead.readLine()) != null) {
                System.out.println("L" + this.numOfReader + ": " + supportVar);
            }
            // Chiusura del BufferedReader
            buffRead.close();
        } catch(FileNotFoundException fnfe) {
            System.out.println("Il file di supporto non e' stato trovato");
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            System.out.println("Errore di natura I/O in lettura");
            ioe.printStackTrace();
        }
    }
    
    // Implemenrazione del Metodo run
    @Override
    public void run() {
        for(int i=0; i<3; i++) {
            read();
        }
    }
}

// Scrittori
class ReadersAndWritersWriter implements Runnable {
    
    // Identificativo dello scrittore
    private int numOfWriter;
    // Risorsa condivisa
    private File file;
    // Lock di scrittura (Mutualmente esclusivo)
    private Lock writeLock;

    // Costruttore
    protected ReadersAndWritersWriter(int numOfWriter, File sharedResource, Lock writeLock) {
        this.numOfWriter = numOfWriter;
        this.file = sharedResource;
        this.writeLock = writeLock;
        System.out.println("Processo scrittore numero " + numOfWriter + " creato");
    }

    // Routine di Scrittura
    private void write() {
        // Acquisizione del Lock di scrittura
        writeLock.lock();
        System.out.println("R" + this.numOfWriter + ": Sono il Processo scrittore numero " + this.numOfWriter +
                " ed ho acquisito il file");
        try {
            System.out.println("R" + this.numOfWriter + ": Sono il Processo scrittore numero " + this.numOfWriter +
                " e sto aggiungendo informazioni al file");
            addFileContent();
        } finally {
            System.out.println("R" + this.numOfWriter + ": Sono il Processo scrittore numero " + this.numOfWriter +
            " e sto rilasciando il file");
            // Rilascio del Lock di scrittura
            writeLock.unlock();
        }
    }

    // Scrittura su file
    private void addFileContent() {
        try {
            // Utilizzo di un PrintWriter per l'operazione di Append di una stringa
            PrintWriter pw = new PrintWriter(new FileWriter(file, true));
            // Aggiunta della stringa al file
            pw.append("Modifica effettuata dallo scrittore numero " + numOfWriter + "\n");
            // Chiusura del PrintWriter
            pw.close();
        } catch(FileNotFoundException fnfe) {
            System.out.println("Il file di supporto non e' stato trovato");
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            System.out.println("Errore di natura I/O in lettura");
            ioe.printStackTrace();
        }
    }
    
    // Implementazione del Metodo run
    @Override
    public void run() {
        for(int i=0; i<2; i++) {
            write();
        }
    }
}
