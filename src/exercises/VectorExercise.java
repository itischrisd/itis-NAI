package exercises;

import generic.Algebra;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class VectorExercise {

    private static final int VECTOR_DIMENSION = 3;
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    public static void addingVectors() {
        System.out.println("Dodaj do siebie poniższe wektory");
        List<Double> a = randomVector(VECTOR_DIMENSION);
        List<Double> b = randomVector(VECTOR_DIMENSION);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        System.out.println("Podaj wynik dodawania wektorów a i b (współrzędne wektora wynikowego oddzielone spacją)");
        List<Double> userResult = new ArrayList<>();
        for (int i = 0; i < VECTOR_DIMENSION; i++) {
            userResult.add(scanner.nextDouble());
        }

        List<Double> result = Algebra.addVectors(a, b);

        if (userResult.equals(result)) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + result);
        }
    }

    public static void multiplyingByScalar() {
        System.out.println("Pomnóż poniższy wektor przez skalar");
        List<Double> a = randomVector(VECTOR_DIMENSION);
        double b = random.nextInt(11) - 5;

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        System.out.println("Podaj wynik mnożenia (współrzędne wektora wynikowego oddzielone spacją)");
        List<Double> userResult = new ArrayList<>();
        for (int i = 0; i < VECTOR_DIMENSION; i++) {
            userResult.add(scanner.nextDouble());
        }

        List<Double> result = Algebra.multiplyVectorByScalar(a, b);

        if (userResult.equals(result)) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + result);
        }
    }

    public static void dotProduct() {
        System.out.println("Oblicz iloczyn skalarny poniższych wektorów");
        List<Double> a = randomVector(VECTOR_DIMENSION);
        List<Double> b = randomVector(VECTOR_DIMENSION);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        System.out.println("Podaj wynik mnożenia skalarnego");
        double userResult = scanner.nextDouble();

        double result = Algebra.dotProduct(a, b);

        if (userResult == result) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + result);
        }
    }

    public static void vectorLength() {
        System.out.println("Oblicz długość poniższego wektora");
        List<Double> a = randomVector(VECTOR_DIMENSION);

        System.out.println("a = " + a);

        System.out.println("Podaj wynik obliczenia długości wektora");
        double userResult = scanner.nextDouble();

        double result = Algebra.vectorLength(a);

        if (userResult == result) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + result);
        }
    }

    public static void distance() {
        System.out.println("Oblicz odległość między poniższymi wektorami");
        List<Double> a = randomVector(VECTOR_DIMENSION);
        List<Double> b = randomVector(VECTOR_DIMENSION);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        System.out.println("Podaj wynik obliczenia odległości między wektorami");
        double userResult = scanner.nextDouble();

        double result = Algebra.distance(a, b);

        if (userResult == result) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + result);
        }
    }

    public static void areOrthogonal() {
        System.out.println("Sprawdź czy poniższe wektory są prostopadłe");
        List<Double> a = randomVector(VECTOR_DIMENSION);
        List<Double> b = randomVector(VECTOR_DIMENSION);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        System.out.println("Podaj wynik sprawdzenia czy wektory są prostopadłe (true/false)");
        boolean userResult = scanner.nextBoolean();

        boolean result = Algebra.angle(a, b) == Math.PI / 2;

        if (userResult == result) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + result);
        }
    }

    public static void angle() {
        System.out.println("Oblicz kąt między poniższymi wektorami");
        List<Double> a = randomVector(VECTOR_DIMENSION);
        List<Double> b = randomVector(VECTOR_DIMENSION);

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        System.out.println("Podaj wynik obliczenia kąta między wektorami (w stopniach)");
        double userResult = scanner.nextDouble();

        double result = Algebra.angle(a, b);
        result = Math.round(result * 180 / Math.PI);

        if (userResult == result) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + result);
        }
    }

    public static List<Double> randomVector(int dimension) {
        List<Double> vector = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            vector.add((double) (random.nextInt(11) - 5));
        }
        return vector;
    }
}
