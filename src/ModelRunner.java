import bayes.NaiveBayes;
import clustering.KMeans;
import decision.DecisionTree;
import generic.DataPointCollector;
import generic.NominalDataSet;
import generic.NumericDataSet;
import knn.NearestNeighbors;
import networks.MultiLayer;
import networks.NetworkTeacher;
import networks.SingleLayer;
import optimisation.Knapsack;
import perceptron.Perceptron;
import perceptron.PerceptronTeacher;
import perceptron.activation.SigmoidUnipolar;
import perceptron.activation.StepUnipolar;

import java.util.*;

public class ModelRunner {

    private static final Scanner scanner = new Scanner(System.in);
    private static NominalDataSet nominalDataSet;
    private static NumericDataSet numericDataSet;
    private static List<Double> numericDataPoint;
    private static List<String> nominalDataPoint;
    private static Perceptron perceptron;
    private static SingleLayer singleLayer;
    private static MultiLayer multiLayer;
    private static DecisionTree decisionTree;

    public static void main(String[] args) {


        interact();
    }

    private static void interact() {
        boolean exit = false;

        System.out.println("""
                Wybierz operację:
                1. Załaduj data set
                2. Podaj dane do klasyfikacji
                3. Uruchom kNN
                4. Wytrenuj Perceptron
                5. Wytrenuj sieć jednowarstwową
                6. Wytrenuj sieć wielowarstwową
                7. Prztrenowanie sieci wielowarstwowej (ekstremalna minimalizacja błędu)
                8. Drzewo decyzyjne
                9. Wypisz data set
                10. Walidacja krzyżowa stałej uczącej
                11. Naiwny klasyfikator Bayesa
                12. k-średnich
                13. Problem plecakowy
                14. Wyjdź""");

        int model = scanner.nextInt();

        switch (model) {
            case 1 -> loadDataSet();
            case 2 -> collectDataPoint();
            case 3 -> kNN();
            case 4 -> perceptron();
            case 5 -> singleLayer();
            case 6 -> multiLayer();
            case 7 -> multiLayerErrorBenchmark();
            case 8 -> decisionTree();
            case 9 -> printDataSet();
            case 10 -> crossValidation();
            case 11 -> naiveBayes();
            case 12 -> kMeans();
            case 13 -> knapsack();
            case 14 -> exit = true;
            default -> System.out.println("Niepoprawny numer modelu!");
        }

        if (!exit) {
            interact();
        }
    }

    private static void knapsack() {
        int numberOfItems = 10;
        Knapsack knapsack = new Knapsack(numberOfItems);
        System.out.println("Wygenerowany zestaw przedmiotów:");
        knapsack.printSolution(new ArrayList<>(Collections.nCopies(numberOfItems, 1)));
        System.out.println("Najlepsze rozwiązanie (brute force):");
        knapsack.printSolution(knapsack.bruteForce());
        System.out.println("Najlepsze rozwiązanie (metoda zachłanna):");
        knapsack.printSolution(knapsack.greedy());
    }

    private static void kMeans() {
        if (numericDataSet == null) {
            System.out.println("Najpierw załaduj data set numeryczny!");
            return;
        }
        int k = 3;
        KMeans kMeans = new KMeans(numericDataSet);
        kMeans.cluster(k);
        kMeans.printClusters();
    }

    private static void naiveBayes() {
        if (nominalDataSet == null) {
            System.out.println("Najpierw załaduj data set nominalny!");
            return;
        }
        System.out.println("Podaj dane do klasyfikacji:");
        List<String> testPoint = DataPointCollector.collectNominal(nominalDataSet.getAttributeNames());
        NaiveBayes naiveBayes = new NaiveBayes(nominalDataSet);
        System.out.println("Prawdopodobieństwa przynależności punktu testowego do klas:");
        System.out.println(naiveBayes.calculateProbabilities(testPoint));
        System.out.println("Klasa, do której zaklasyfikowano punkt testowy: " + naiveBayes.decide(testPoint));
    }

    private static void collectDataPoint() {
        System.out.println("""
                Wybierz rodzaj data pointu:
                1. Nominalny
                2. Numeryczny""");

        int set = scanner.nextInt();

        switch (set) {
            case 1 -> {
                nominalDataPoint = DataPointCollector.collectNominal(nominalDataSet.getAttributeNames());
                if (decisionTree != null) {
                    System.out.println("Predykcja drzewa decyzyjnego: " + decisionTree.classify(nominalDataPoint));
                }
            }
            case 2 -> {
                numericDataPoint = DataPointCollector.collectNumeric(numericDataSet.getAttributeNames());
                if (perceptron != null) {
                    System.out.println("Predykcja perceptronu: " + perceptron.calcualte(numericDataPoint));
                }
                if (singleLayer != null) {
                    System.out.println("Predykcja sieci jednowarstwowej: " + singleLayer.calculate(numericDataPoint));
                }
                if (multiLayer != null) {
                    System.out.println("Predykcja sieci wielowarstwowej: " + multiLayer.calculate(numericDataPoint));
                }
            }
            default -> System.out.println("Niepoprawny numer typu!");
        }
    }

    private static void loadDataSet() {
        System.out.println("""
                Wybierz rodzaj data setu:
                1. Nominalny
                2. Numeryczny""");

        int set = scanner.nextInt();

        switch (set) {
            case 1 -> loadNominalDataSet();
            case 2 -> loadNumericDataSet();
            default -> System.out.println("Niepoprawny numer setu!");
        }
    }

    private static void loadNominalDataSet() {
        System.out.println("""
                Wybierz data set:
                1. tennis.csv
                2. glasses.csv""");

        int set = scanner.nextInt();

        switch (set) {
            case 1 -> nominalDataSet = NominalDataSet.parseCSV("data/tennis.csv");
            case 2 -> nominalDataSet = NominalDataSet.parseCSV("data/glasses.csv");
            default -> System.out.println("Niepoprawny numer setu!");
        }
    }

    private static void loadNumericDataSet() {
        System.out.println("""
                Wybierz data set:
                1. iris.csv""");

        int set = scanner.nextInt();

        switch (set) {
            case 1 -> numericDataSet = NumericDataSet.parseCSV("data/iris.csv");
            default -> System.out.println("Niepoprawny numer setu!");
        }
    }

    private static void kNN() {
        if (numericDataSet == null) {
            System.out.println("Najpierw załaduj data set numeryczny!");
            return;
        }
        List<Double> testDataPoint = Arrays.asList(5.7, 4.4, 1.5, .4);
        NearestNeighbors.calculate(7, numericDataSet, testDataPoint);
    }

    private static void perceptron() {
        if (numericDataSet == null) {
            System.out.println("Najpierw załaduj data set numeryczny!");
            return;
        }
        perceptron = new Perceptron(new StepUnipolar(), numericDataSet.getAttributeNames().size());
        double splitRatio = 0.75;
        PerceptronTeacher.teach(perceptron, numericDataSet, "Setosa", splitRatio);
        if (numericDataPoint != null) {
            System.out.println("Predykcja perceptronu: " + perceptron.calcualte(numericDataPoint));
        }
        PerceptronTeacher.evaluate(perceptron, numericDataSet, "Setosa");
    }

    private static void singleLayer() {
        if (numericDataSet == null) {
            System.out.println("Najpierw załaduj data set numeryczny!");
            return;
        }
        List<String> classes = numericDataSet.getAllDecisions().stream().distinct().sorted().toList();
        singleLayer = new SingleLayer(new SigmoidUnipolar(), classes, numericDataSet.getAttributeNames().size());
        double learningRate = 0.001;
        double errorThreshold = 13.6;
        double splitRatio = 0.75;
        NetworkTeacher.teach(singleLayer, numericDataSet, learningRate, errorThreshold, splitRatio);
        NetworkTeacher.evaluate(singleLayer, numericDataSet);
        if (numericDataPoint != null) {
            System.out.println("Predykcja sieci jednowarstwowej: " + singleLayer.calculate(numericDataPoint));
        }
    }

    private static void multiLayer() {
        if (numericDataSet == null) {
            System.out.println("Najpierw załaduj data set numeryczny!");
            return;
        }
        List<String> classes = numericDataSet.getAllDecisions().stream().distinct().sorted().toList();
        multiLayer = new MultiLayer(Arrays.asList(new SigmoidUnipolar(), new SigmoidUnipolar()), classes, numericDataSet.getAttributeNames().size());
        double learningRate = 0.01;
        double errorThreshold = 2.9;
        double splitRatio = 0.75;
        NetworkTeacher.teach(multiLayer, numericDataSet, learningRate, errorThreshold, splitRatio);
        NetworkTeacher.evaluate(multiLayer, numericDataSet);
        if (numericDataPoint != null) {
            System.out.println("Predykcja sieci wielowarstwowej: " + multiLayer.calculate(numericDataPoint));
        }
    }

    private static void multiLayerErrorBenchmark() {
        if (numericDataSet == null) {
            System.out.println("Najpierw załaduj data set numeryczny!");
            return;
        }
        List<String> classes = numericDataSet.getAllDecisions().stream().distinct().sorted().toList();
        multiLayer = new MultiLayer(Arrays.asList(new SigmoidUnipolar(), new SigmoidUnipolar()), classes, numericDataSet.getAttributeNames().size());
        double learningRate = 0.01;
        double errorThreshold = 2.9;
        NetworkTeacher.optimizeByErrorThreshold(multiLayer, numericDataSet, learningRate, errorThreshold);
        if (numericDataPoint != null) {
            System.out.println("Predykcja sieci wielowarstwowej: " + multiLayer.calculate(numericDataPoint));
        }
    }

    private static void decisionTree() {
        if (nominalDataSet == null) {
            System.out.println("Najpierw załaduj data set nominalny!");
            return;
        }

        decisionTree = new DecisionTree(nominalDataSet);
        decisionTree.printTree();

        if (nominalDataPoint != null) {
            System.out.println("Predykcja drzewa decyzyjnego: " + decisionTree.classify(nominalDataPoint));
        }
    }

    private static void printDataSet() {
        if (nominalDataSet != null) {
            nominalDataSet.print();
        }
        if (numericDataSet != null) {
            numericDataSet.print();
        }
    }

    private static void crossValidation() {
        if (numericDataSet == null) {
            System.out.println("Najpierw załaduj data set numeryczny!");
            return;
        }

        List<Double> learningRates = Arrays.asList(0.001, 0.01, 0.05, 0.1, 0.2);
        double errorThreshold = 2.9;
        int folds = 10;
        double splitRatio = 0.75;
        double bestLearningRate = NetworkTeacher.crossValidate(numericDataSet, learningRates, errorThreshold, folds, splitRatio);
        System.out.println("Najlepsza stała ucząca: " + bestLearningRate + ", tworzę sieć wielowarstwową...");

        List<String> classes = numericDataSet.getAllDecisions().stream().distinct().sorted().toList();
        multiLayer = new MultiLayer(Arrays.asList(new SigmoidUnipolar(), new SigmoidUnipolar()), classes, numericDataSet.getAttributeNames().size());
        NetworkTeacher.teach(multiLayer, numericDataSet, bestLearningRate, errorThreshold, splitRatio);
        if (numericDataPoint != null) {
            System.out.println("Predykcja sieci wielowarstwowej: " + multiLayer.calculate(numericDataPoint));
        }
    }
}