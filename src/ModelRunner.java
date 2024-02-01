import generic.DataSet;
import knn.NearestNeighbors;
import networks.MultiLayer;
import networks.NetworkTeacher;
import networks.SingleLayer;
import perceptron.Perceptron;
import perceptron.Teacher;
import perceptron.activation.SigmoidUnipolar;
import perceptron.activation.StepUnipolar;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ModelRunner {

    private static DataSet dataSet;
    private static List<Double> setosaPoint;
    private static List<Double> virginicaPoint;
    private static List<Double> versicolorPoint;

    public static void main(String[] args) {

        dataSet = DataSet.parseCSV("data/iris.csv");
        setosaPoint = Arrays.asList(5.7, 4.4, 1.5, 0.4);
        virginicaPoint = Arrays.asList(6.7, 3.3, 5.7, 2.5);
        versicolorPoint = Arrays.asList(4.9, 2.4, 3.3, 1.0);

        System.out.println("""
                Wybierz model:
                1. kNN
                2. Perceptron
                3. Sieć jednowarstwowa
                4. Sieć wielowarstwowa
                5. Benchmark sieci wielowarstwowej""");

        Scanner scanner = new Scanner(System.in);
        int model = scanner.nextInt();

        switch (model) {
            case 1 -> kNN();
            case 2 -> perceptron();
            case 3 -> singleLayer();
            case 4 -> multiLayer();
            case 5 -> multiLayerErrorBenchmark();
            default -> System.out.println("Niepoprawny numer modelu!");
        }
    }

    private static void kNN() {
        List<Double> testDataPoint = Arrays.asList(5.7, 4.4, 1.5, .4);
        NearestNeighbors.calculate(7, dataSet, testDataPoint);
    }

    private static void perceptron() {
        Perceptron perceptron = new Perceptron(new StepUnipolar(), dataSet.getAttributeNames().size());
        Teacher.teach(perceptron, dataSet, "Setosa");
        double result = perceptron.calcualte(setosaPoint);
        System.out.println("Perceptron prediction: " + result);
        System.out.println(perceptron);
    }

    private static void singleLayer() {
        List<String> classes = dataSet.getDecisions().stream().distinct().sorted().toList();
        SingleLayer singleLayer = new SingleLayer(new SigmoidUnipolar(), classes, dataSet.getAttributeNames().size());
        double learningRate = 0.001;
        double errorThreshold = 13.6;
        NetworkTeacher.teach(singleLayer, dataSet, learningRate, errorThreshold);
        System.out.println("Single layer prediction for Setosa: " + singleLayer.decide(setosaPoint));
        System.out.println("Single layer prediction for Virginica: " + singleLayer.decide(virginicaPoint));
        System.out.println("Single layer prediction for Versicolor: " + singleLayer.decide(versicolorPoint));
    }

    private static void multiLayer() {
        List<String> classes = dataSet.getDecisions().stream().distinct().sorted().toList();
        MultiLayer multiLayer = new MultiLayer(Arrays.asList(new SigmoidUnipolar(), new SigmoidUnipolar()), classes, dataSet.getAttributeNames().size());
        double learningRate = 0.001;
        double errorThreshold = 2.9;
        NetworkTeacher.teach(multiLayer, dataSet, learningRate, errorThreshold);
        System.out.println("Multi layer prediction for Setosa: " + multiLayer.decide(setosaPoint));
        System.out.println("Multi layer prediction for Virginica: " + multiLayer.decide(virginicaPoint));
        System.out.println("Multi layer prediction for Versicolor: " + multiLayer.decide(versicolorPoint));
    }

    private static void multiLayerErrorBenchmark() {
        List<String> classes = dataSet.getDecisions().stream().distinct().sorted().toList();
        MultiLayer multiLayer = new MultiLayer(Arrays.asList(new SigmoidUnipolar(), new SigmoidUnipolar()), classes, dataSet.getAttributeNames().size());
        double learningRate = 0.001;
        double errorThreshold = 2.9;
        NetworkTeacher.optimizeByErrorThreshold(multiLayer, dataSet, learningRate, errorThreshold);
        System.out.println("Multi layer prediction for Setosa: " + multiLayer.decide(setosaPoint));
        System.out.println("Multi layer prediction for Virginica: " + multiLayer.decide(virginicaPoint));
        System.out.println("Multi layer prediction for Versicolor: " + multiLayer.decide(versicolorPoint));
    }
}