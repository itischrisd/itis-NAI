package perceptron;

import generic.NumericDataSet;

import java.util.List;

public class PerceptronTeacher {

    public static void teach(Perceptron perceptron, NumericDataSet numericDataSet, String classValue, double splitRatio) {
        numericDataSet.splitToTrainAndTest(splitRatio);
        List<String> decisions = numericDataSet.getTrainDecisions();
        List<List<Double>> dataPoints = numericDataSet.getTrainDataPoints();
        boolean complete = false;
        int counter = 0;
        while (!complete) {
            complete = true;
            for (int i = 0; i < dataPoints.size(); i++) {
                int d = classValue.equals(decisions.get(i)) ? 1 : 0;
                double y = perceptron.learn(dataPoints.get(i), d, 0.0001);
                complete = complete && d == y;
            }
            counter++;
            if (counter % 100 == 0) {
                System.out.println("Minęło epok: " + counter);
            }
            if (counter > 10000)
                break;
        }
    }

    public static void evaluate(Perceptron perceptron, NumericDataSet numericDataSet, String classValue) {
        List<String> decisions = numericDataSet.getTestDecisions();
        List<List<Double>> dataPoints = numericDataSet.getTestDataPoints();
        int correct = 0;
        for (int i = 0; i < dataPoints.size(); i++) {
            double d = decisions.get(i).equals(classValue) ? 1 : 0;
            double prediction = perceptron.calcualte(dataPoints.get(i));
            if (d == prediction) {
                correct++;
            }
        }
        double accuracy = (double) correct / numericDataSet.getTestDataPoints().size();
        System.out.println("Wszystkich przypadków: " + numericDataSet.getTestDataPoints().size());
        System.out.println("Poprawnie zaklasyfikowanych: " + correct);
        System.out.println("Dokładność: " + accuracy);
    }
}
