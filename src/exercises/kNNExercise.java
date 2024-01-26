package exercises;

import generic.DataSet;
import knn.NearestNeighbors;

import java.util.*;

import static exercises.VectorExercise.randomVector;


public class kNNExercise {

    private static final int VECTOR_DIMENSION = 3;
    private static final int K = 3;
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {
        List<String> decisions = Arrays.asList("A", "B", "A", "B", "C", "C");
        List<List<Double>> vectors = new ArrayList<>();
        vectors.add(randomVector(VECTOR_DIMENSION));
        vectors.add(randomVector(VECTOR_DIMENSION));
        vectors.add(randomVector(VECTOR_DIMENSION));
        vectors.add(randomVector(VECTOR_DIMENSION));
        vectors.add(randomVector(VECTOR_DIMENSION));
        vectors.add(randomVector(VECTOR_DIMENSION));

        DataSet dataSet = new DataSet(vectors, decisions);

        System.out.println("Mając podane wektory należące do 3 kategorii (klas):");

        System.out.println("klasa A:");
        System.out.println("a = " + vectors.get(0));
        System.out.println("c = " + vectors.get(1));

        System.out.println("klasa B:");
        System.out.println("b = " + vectors.get(2));
        System.out.println("d = " + vectors.get(3));

        System.out.println("klasa C:");
        System.out.println("e = " + vectors.get(4));
        System.out.println("f = " + vectors.get(5));

        List<Double> x = randomVector(VECTOR_DIMENSION);

        System.out.print("Zaklasyfikuj wektor x = " + x);
        System.out.println(" używając algorytmu k-NN (k nearest neighbours) dla k = " + K + " i zwykłej odległości euklidesowej.");

        System.out.println("\nJaka jest klasa wektora x? (A, B, C)");
        String userResult = scanner.nextLine().trim().toUpperCase();

        String result = NearestNeighbors.calculate(K, dataSet, x);

        if (userResult.equals(result)) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowa klasa to: " + result);
        }
    }
}
