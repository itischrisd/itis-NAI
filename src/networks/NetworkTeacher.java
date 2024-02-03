package networks;

import generic.NumericDataSet;

import java.util.List;

public class NetworkTeacher {

    public static void teach(Network network, NumericDataSet numericDataSet, double learningRate, double errorThreshold) {
        List<String> decisions = numericDataSet.getDecisions();
        double error = Double.MAX_VALUE;
        int counter = 0;
        while (!(error < errorThreshold)) {
            counter++;
            error = 0.0;
            for (int i = 0; i < numericDataSet.getDataPoints().size(); i++) {
                String d = decisions.get(i);
                error += network.learn(numericDataSet.getDataPoints().get(i), d, learningRate);
            }
            if (counter % 1000 == 0) {
                System.out.println("Epochs passed: " + counter + ", error: " + error);
            }
        }
    }

    public static void optimizeByErrorThreshold(Network network, NumericDataSet numericDataSet, double learningRate, double errorThreshold) {
        List<String> decisions = numericDataSet.getDecisions();
        double error = Double.MAX_VALUE;
        double previousError = Double.MAX_VALUE;
        int counter = 0;
        while (!(error < errorThreshold) || previousError > error) {
            counter++;
            previousError = error;
            error = 0.0;
            for (int i = 0; i < numericDataSet.getDataPoints().size(); i++) {
                String d = decisions.get(i);
                error += network.learn(numericDataSet.getDataPoints().get(i), d, learningRate);
            }
            if (counter % 1000 == 0) {
                System.out.println("Epochs passed: " + counter + ", error: " + error);
            }
        }
    }
}
