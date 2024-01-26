package exercises;

import generic.Algebra;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MatrixExercise {

    private static final int MATRIX_DIMENSION = 3;
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    public static void transpose() {
        System.out.println("Transponuj poniższą macierz");
        List<List<Double>> matrix = randomMatrix();

        System.out.println("A = " + prettify(matrix));

        System.out.println("Podaj wynik transponowania (wiersze macierzy wynikowej oddzielone enterem, a współrzędne wiersza oddzielone spacją)");
        List<List<Double>> userResult = getUserResult();

        List<List<Double>> result = Algebra.transpose(matrix);

        if (userResult.equals(result)) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + result);
        }
    }

    public static void multiply() {
        System.out.println("Pomnóż poniższe macierze");
        List<List<Double>> matrix1 = randomMatrix();
        List<List<Double>> matrix2 = randomMatrix();

        System.out.println("A = " + prettify(matrix1));
        System.out.println("B = " + prettify(matrix2));

        System.out.println("Podaj wynik mnożenia (wiersze macierzy wynikowej oddzielone enterem, a współrzędne wiersza oddzielone spacją)");
        List<List<Double>> userResult = getUserResult();

        List<List<Double>> result = Algebra.multiplyMatrixByMatrix(matrix1, matrix2);

        if (userResult.equals(result)) {
            System.out.println("Poprawna odpowiedź!");
        } else {
            System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + prettify(result));
        }
    }

    private static List<List<Double>> randomMatrix() {
        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < MATRIX_DIMENSION; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < MATRIX_DIMENSION; j++) {
                row.add((double) (random.nextInt(11) - 5));
            }
            matrix.add(row);
        }
        return matrix;
    }

    private static String prettify(List<List<Double>> matrix) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (List<Double> row : matrix) {
            stringBuilder.append("[");
            for (Double value : row) {
                stringBuilder.append(value).append(", ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            stringBuilder.append("]\n");
        }
        return stringBuilder.toString();
    }

    private static List<List<Double>> getUserResult() {
        List<List<Double>> userResult = new ArrayList<>();
        for (int i = 0; i < MATRIX_DIMENSION; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < MATRIX_DIMENSION; j++) {
                row.add(scanner.nextDouble());
            }
            userResult.add(row);
        }
        return userResult;
    }
}
