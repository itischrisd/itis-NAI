package exercises;

import generic.Algebra;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static exercises.VectorExercise.randomVector;

public class PerceptronExercise {

    private static final int VECTOR_DIMENSION = 3;
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {
        List<Double> p = randomVector(VECTOR_DIMENSION);
        List<Double> q = randomVector(VECTOR_DIMENSION);
        int r = random.nextInt(5) - 2;

        System.out.println("Dany jest wektor wejściowy p=" + p + " oraz wektor wag perceptronu q=" + q + " i próg r=" + r + " dyskretnego perceptronu unipolarnego.");
        System.out.println("a) Oblicz wyjście tego perceptronu.");
        System.out.println("b) Zakładając, że wyjście jest nieprawidłowe, oblicz, używając reguły uczenia delta, zmodyfikowany wektor wag, zakładając, że stała ucząca wynosi a=1.");
        System.out.println("c) Czy wartość progu podniesie się, czy obniży?");

        System.out.println("Podaj wynik działania perceptronu (0 lub 1)");
        int userActivation = scanner.nextInt();
        int activation = Algebra.dotProduct(p, q) >= r ? 1 : 0;
        
        if (userActivation == activation) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + activation);
        }

        System.out.println("Podaj nowy wektor wag (współrzędne wektora wynikowego oddzielone spacją)");
        List<Double> userQ = new ArrayList<>();
        for (int i = 0; i < q.size(); i++) {
            userQ.add(scanner.nextDouble());
        }
        List<Double> newQ = Algebra.addVectors(q, Algebra.multiplyVectorByScalar(p, activation == 1 ? -1 : 1));

        if (userQ.equals(newQ)) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + newQ);
        }

        System.out.println("Czy wartość progu podniesie się, czy obniży? (true/false)");
        boolean userR = scanner.nextBoolean();
        boolean newR = r - (activation == 1 ? -1 : 1) > r;

        if (userR == newR) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + newR);
        }
    }
}
