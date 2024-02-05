package generic;

import perceptron.activation.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class Algebra {

    public static double dotProduct(List<Double> vector1, List<Double> vector2) {
        double sum = 0;
        for (int i = 0; i < vector1.size(); i++) {
            sum += vector1.get(i) * vector2.get(i);
        }
        return sum;
    }

    public static List<Double> multiplyVectorByScalar(List<Double> vector, double scalar) {
        List<Double> result = new ArrayList<>();
        for (Double value : vector) {
            result.add(value * scalar);
        }
        return result;
    }

    public static List<Double> addVectors(List<Double> vector1, List<Double> vector2) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < vector1.size(); i++) {
            result.add(vector1.get(i) + vector2.get(i));
        }
        return result;
    }

    public static List<Double> subtractVectors(List<Double> vector1, List<Double> vector2) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < vector1.size(); i++) {
            result.add(vector1.get(i) - vector2.get(i));
        }
        return result;
    }

    public static List<Double> normalize(List<Double> vector) {
        double length = Math.sqrt(dotProduct(vector, vector));
        return multiplyVectorByScalar(vector, 1 / length);
    }

    public static List<Double> multiplyMatrixByVector(List<List<Double>> matrix, List<Double> vector) {
        List<Double> result = new ArrayList<>();
        for (List<Double> row : matrix) {
            result.add(dotProduct(row, vector));
        }
        return result;
    }

    public static List<List<Double>> transpose(List<List<Double>> matrix) {
        List<List<Double>> result = new ArrayList<>();
        for (int i = 0; i < matrix.getFirst().size(); i++) {
            List<Double> row = new ArrayList<>();
            for (List<Double> column : matrix) {
                row.add(column.get(i));
            }
            result.add(row);
        }
        return result;
    }

    public static double vectorLength(List<Double> a) {
        return Math.sqrt(dotProduct(a, a));
    }

    public static double distance(List<Double> a, List<Double> b) {
        return Math.sqrt(dotProduct(subtractVectors(a, b), subtractVectors(a, b)));
    }

    public static double angle(List<Double> a, List<Double> b) {
        return Math.acos(dotProduct(a, b) / (vectorLength(a) * vectorLength(b)));
    }

    public static List<List<Double>> multiplyMatrixByMatrix(List<List<Double>> matrix1, List<List<Double>> matrix2) {
        List<List<Double>> result = new ArrayList<>();
        for (List<Double> row : matrix1) {
            result.add(multiplyMatrixByVector(matrix2, row));
        }
        return result;
    }

    public static List<Double> applyDerivative(List<Double> vector, ActivationFunction activationFunction) {
        List<Double> result = new ArrayList<>();
        for (Double value : vector) {
            result.add(activationFunction.derivative(value));
        }
        return result;
    }

    public static List<Double> applyActivationFunction(List<Double> vector, ActivationFunction activationFunction) {
        List<Double> result = new ArrayList<>();
        for (Double value : vector) {
            result.add(activationFunction.calculate(value));
        }
        return result;
    }

    public static List<Double> multiplyVectorElements(List<Double> vector1, List<Double> vector2) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < vector1.size(); i++) {
            result.add(vector1.get(i) * vector2.get(i));
        }
        return result;
    }

    public static List<Double> centroid(List<List<Double>> vectors) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < vectors.getFirst().size(); i++) {
            double sum = 0;
            for (List<Double> vector : vectors) {
                sum += vector.get(i);
            }
            result.add(sum / vectors.size());
        }
        return result;
    }
}
