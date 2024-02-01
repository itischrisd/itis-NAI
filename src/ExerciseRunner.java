import exercises.*;

import java.util.Scanner;

public class ExerciseRunner {

    public static void main(String[] args) {
        System.out.println("""
                Wybierz ćwiczenie:
                1. Dodawanie wektorów
                2. Mnożenie wektora przez skalar
                3. Iloczyn skalarny
                4. Długość wektora
                5. Odległość między wektorami
                6. Czy wektory są prostopadłe?
                7. Kąt między wektorami
                8. Transpozycja macierzy
                9. Mnożenie macierzy
                10. kNN
                11. Perceptron
                12. Ewaluacja modeli""");

        Scanner scanner = new Scanner(System.in);
        int exercise = scanner.nextInt();

        switch (exercise) {
            case 1 -> VectorExercise.addingVectors();
            case 2 -> VectorExercise.multiplyingByScalar();
            case 3 -> VectorExercise.dotProduct();
            case 4 -> VectorExercise.vectorLength();
            case 5 -> VectorExercise.distance();
            case 6 -> VectorExercise.areOrthogonal();
            case 7 -> VectorExercise.angle();
            case 8 -> MatrixExercise.transpose();
            case 9 -> MatrixExercise.multiply();
            case 10 -> kNNExercise.run();
            case 11 -> PerceptronExercise.run();
            case 12 -> EvaluationExercise.run();
            default -> System.out.println("Niepoprawny numer ćwiczenia!");
        }
    }
}
