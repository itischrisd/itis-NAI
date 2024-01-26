package exercises;

import java.util.Random;
import java.util.Scanner;

public class EvaluationExercise {

    public static void run() {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int totalCases = random.nextInt(51) + 50;
        int predictedPositive = random.nextInt(totalCases / 2) + 1;
        int truePositive = random.nextInt(predictedPositive + 1);
        int trueNegative = random.nextInt((totalCases - predictedPositive) + 1);

        int predictedNegative = totalCases - predictedPositive;
        int falsePositive = predictedPositive - truePositive;
        int falseNegative = predictedNegative - trueNegative;

        System.out.println("Zadanie: Ewaluacja klasyfikatora");
        System.out.println("Zbiór testowy zawiera " + totalCases + " przypadków (obserwacji) dotyczących danych pacjentów chorych na pewną chorobę i poddanych pewnej kuracji.");
        System.out.println("Dla " + predictedPositive + " pacjentów ze zbioru testowego system przewidział, że kuracja będzie skuteczna, przy czym w " + truePositive + " przypadkach była to faktycznie prawda.");
        System.out.println("Ponadto system prawidłowo przewidział, że w " + trueNegative + " przypadkach kuracja będzie nieskuteczna.");

        System.out.println("\nStwórz macierz omyłek i oblicz:");
        System.out.println("a) TP, FN, FP, FN");
        System.out.println("b) dokładność klasyfikatora");
        System.out.println("c) precyzję");
        System.out.println("d) pełność");
        System.out.println("e) F-miarę");
        System.out.println("\n(UWAGA: proszę podać wynik w postaci ułamka zwykłego maksymalnie uproszczonego)");

        System.out.println("\nPodaj TP");
        int userTP = scanner.nextInt();
        System.out.println("Podaj FN");
        int userFN = scanner.nextInt();
        System.out.println("Podaj FP");
        int userFP = scanner.nextInt();
        System.out.println("Podaj TN");
        int userTN = scanner.nextInt();

        if (userTP == truePositive && userFN == falseNegative && userFP == falsePositive && userTN == trueNegative) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: TP=" + truePositive + ", FN=" + falseNegative + ", FP=" + falsePositive + ", TN=" + trueNegative);
        }

        System.out.println("\nPodaj dokładność klasyfikatora (najpierw licznik, potem mianownik)");
        double userAccuracyNumerator = scanner.nextInt();
        double userAccuracyDenominator = scanner.nextInt();

        if (userAccuracyNumerator / userAccuracyDenominator == ((double)truePositive + (double)trueNegative) / (double)totalCases) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik przed uproszczeniem to: " + (truePositive + trueNegative) + "/" + totalCases);
        }

        System.out.println("\nPodaj precyzję (najpierw licznik, potem mianownik)");
        double userPrecisionNumerator = scanner.nextInt();
        double userPrecisionDenominator = scanner.nextInt();

        if (userPrecisionNumerator / userPrecisionDenominator == (double)truePositive / (double)predictedPositive) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik przed uproszczeniem to: " + truePositive + "/" + predictedPositive);
        }

        System.out.println("\nPodaj pełność (najpierw licznik, potem mianownik)");
        double userRecallNumerator = scanner.nextInt();
        double userRecallDenominator = scanner.nextInt();

        if (userRecallNumerator / userRecallDenominator == (double)truePositive / ((double)truePositive + (double)falseNegative)) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik przed uproszczeniem to: " + truePositive + "/" + (truePositive + falseNegative));
        }

        System.out.println("\nPodaj F-miarę (najpierw licznik, potem mianownik)");
        double userFMeasureNumerator = scanner.nextInt();
        double userFMeasureDenominator = scanner.nextInt();

        if (userFMeasureNumerator / userFMeasureDenominator == 2 * (double)truePositive / (2 * (double)truePositive + (double)falsePositive + (double)falseNegative)) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik przed uproszczeniem to: " + 2 * truePositive + "/" + (2 * truePositive + falsePositive + falseNegative));
        }
    }
}
