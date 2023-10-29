public class BubbleSort {

    public static void main(String[] args) {

        // Raccolgo il numero di Argomenti
        int numberOfArguments = args.length;

        // Controllo che l'utente abbia inserito almeno un argomento, altrimenti esco
        if(numberOfArguments < 1) {
            System.out.println("Il programma ha bisogno di un vettore di numeri interi per essere utilizzato. Il processo attuale terminera'.");
            System.exit(-1);
        } else if(numberOfArguments == 1) {
            // Se il vettore e' banale, esso non verra' processato
            System.out.println("E' stato fornito in input un vettore di valori interi banale, il risultato dell'ordinamento e' pari all'input, cioe' " + args[0]);
            System.exit(0);
        } else {
            // Supponendo che gli argomenti passati siano tutti numeri interi, costruisco un array di interi nell'ordine imposto dall'utente
            int[] vector = new int[numberOfArguments];
            for(int i=0; i<numberOfArguments; i++) {
                vector[i] = Integer.parseInt(args[i]);
            }

            // Stampo sullo standard output il vettore non ordinato
            System.out.print("Il vettore non ordinato e': [");
            for(int i=0; i<vector.length; i++) {
                if(i == (vector.length-1)) {
                    System.out.print(vector[i]);
                } else {
                    System.out.print(vector[i] + ", ");
                }
            }
            System.out.println("]");

            // Eseguo l'ordinamento
            BubbleSort(vector);

            // Stampo sullo standard output il vettore ordinato
            System.out.print("Il vettore ordinato e': [");
            for(int i=0; i<vector.length; i++) {
                if(i == (vector.length-1)) {
                    System.out.print(vector[i]);
                } else {
                    System.out.print(vector[i] + ", ");
                }
            }
            System.out.println("]");
        }

    }

    private static void BubbleSort(int[] A) {
        
        // Definizione della dimensione della partizione del vettore non ordinata
        int unorderedPartition = A.length;
        
        // Ordinamento
        while(unorderedPartition > 1) {
            // Scorro la parte non ordinata
            for(int i=2; i<unorderedPartition; i++) {
                // Se non e' ordinato, scambia
                if(A[i] < A[i-1]) {
                    int buf = A[i];
                    A[i] = A[i-1];
                    A[i-1] = buf;
                }
            }
            // Dopo lo scambio, riduco la dimensione della partizione del vettore non ordinata
            unorderedPartition = unorderedPartition - 1;
        }

    }

}