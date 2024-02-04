package networks;

import generic.NumericDataSet;
import perceptron.activation.SigmoidUnipolar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkTeacher {

    public static void teach(Network network, NumericDataSet numericDataSet, double learningRate, double errorThreshold, double splitRatio) {
        numericDataSet.splitToTrainAndTest(splitRatio);
        List<String> decisions = numericDataSet.getTrainDecisions();
        List<List<Double>> dataPoints = numericDataSet.getTrainDataPoints();
        double error = Double.MAX_VALUE;
        int counter = 0;
        while (!(error < errorThreshold)) {
            counter++;
            error = 0.0;
            for (int i = 0; i < dataPoints.size(); i++) {
                String d = decisions.get(i);
                error += network.learn(dataPoints.get(i), d, learningRate);
            }
            if (counter % 1000 == 0) {
                System.out.println("Minęło epok: " + counter + ", error: " + error);
            }
        }
    }

    public static void optimizeByErrorThreshold(Network network, NumericDataSet numericDataSet, double learningRate, double errorThreshold) {
        List<String> decisions = numericDataSet.getAllDecisions();
        double error = Double.MAX_VALUE;
        double previousError = Double.MAX_VALUE;
        int counter = 0;
        while (!(error < errorThreshold) || previousError > error) {
            counter++;
            previousError = error;
            error = 0.0;
            for (int i = 0; i < numericDataSet.getAllDataPoints().size(); i++) {
                String d = decisions.get(i);
                error += network.learn(numericDataSet.getAllDataPoints().get(i), d, learningRate);
            }
            if (counter % 1000 == 0) {
                System.out.println("Minęło epok: " + counter + ", error: " + error);
            }
        }
    }

    public static void evaluate(Network network, NumericDataSet numericDataSet) {
        List<String> decisions = numericDataSet.getTestDecisions();
        List<List<Double>> dataPoints = numericDataSet.getTestDataPoints();
        int correct = 0;
        for (int i = 0; i < dataPoints.size(); i++) {
            String d = decisions.get(i);
            String prediction = network.decide(dataPoints.get(i));
            if (d.equals(prediction)) {
                correct++;
            }
        }
        double accuracy = (double) correct / numericDataSet.getTestDataPoints().size();
        System.out.println("Wszystkich przypadków: " + numericDataSet.getTestDataPoints().size());
        System.out.println("Poprawnie zaklasyfikowanych: " + correct);
        System.out.println("Dokładność: " + accuracy);
    }

    public static double crossValidate(NumericDataSet numericDataSet, List<Double> learningRates, double errorThreshold, int folds, double splitRatio) {
        numericDataSet.splitToTrainAndTest(splitRatio);
        numericDataSet.splitForCrossValidation(folds);
        double bestLearningRate = 0.0;
        List<String> classes = numericDataSet.getAllDecisions().stream().distinct().sorted().toList();
        List<String> attributeNames = numericDataSet.getAttributeNames();
        double bestAccuracy = Double.MIN_VALUE;

        System.out.println("Rozpoczynam walidację krzyżową dla stałych uczenia: " + learningRates);

        for (double learningRate : learningRates) {
            double totalAccuracy = 0.0;
            List<List<List<Double>>> crossValidationDataPoints = numericDataSet.getCrossValidationDataPoints();
            List<List<String>> crossValidationDecisions = numericDataSet.getCrossValidationDecisions();
            for (int i = 0; i < crossValidationDecisions.size(); i++) {

                System.out.println("Rozpoczynam uczenie dla zbioru " + (i + 1) + " z " + folds + " dla stałej uczenia " + learningRate + "...");

                List<List<Double>> trainDataPoints = new ArrayList<>();
                List<String> trainDecisions = new ArrayList<>();
                for (int j = 0; j < crossValidationDecisions.size(); j++) {
                    if (j != i) {
                        trainDataPoints.addAll(crossValidationDataPoints.get(j));
                        trainDecisions.addAll(crossValidationDecisions.get(j));
                    }
                }
                MultiLayer multiLayer = new MultiLayer(Arrays.asList(new SigmoidUnipolar(), new SigmoidUnipolar()), classes, numericDataSet.getAttributeNames().size());
                teachForCrossValidation(multiLayer, new NumericDataSet(attributeNames, trainDataPoints, trainDecisions), learningRate, errorThreshold);
                totalAccuracy += evaluateForCrossValidation(multiLayer, new NumericDataSet(attributeNames, crossValidationDataPoints.get(i), crossValidationDecisions.get(i)));
            }

            double accuracy = totalAccuracy / folds;

            if (accuracy > bestAccuracy) {
                bestAccuracy = accuracy;
                bestLearningRate = learningRate;
            }

            System.out.println("Średnia dokładność dla stałej uczenia " + learningRate + ": " + accuracy);
        }

        return bestLearningRate;
    }

    public static void teachForCrossValidation(Network network, NumericDataSet numericDataSet, double learningRate, double errorThreshold) {
        List<String> decisions = numericDataSet.getAllDecisions();
        double error = Double.MAX_VALUE;
        int counter = 0;
        int epochLimit = 100000;
        while (!(error < errorThreshold)) {
            counter++;
            error = 0.0;
            for (int i = 0; i < numericDataSet.getAllDataPoints().size(); i++) {
                String d = decisions.get(i);
                error += network.learn(numericDataSet.getAllDataPoints().get(i), d, learningRate);
            }
            if (counter % 1000 == 0) {
                System.out.println("Minęło epok: " + counter + ", error: " + error);
            }
            if (counter > epochLimit) {
                break;
            }
        }
    }

    public static double evaluateForCrossValidation(Network network, NumericDataSet numericDataSet) {
        List<String> decisions = numericDataSet.getAllDecisions();
        int correct = 0;
        for (int i = 0; i < numericDataSet.getAllDataPoints().size(); i++) {
            String d = decisions.get(i);
            String prediction = network.decide(numericDataSet.getAllDataPoints().get(i));
            if (d.equals(prediction)) {
                correct++;
            }
        }
        return (double) correct / numericDataSet.getAllDataPoints().size();
    }
}
