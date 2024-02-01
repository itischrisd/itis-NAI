package networks;

import generic.DataSet;

import java.util.List;

public class NetworkTeacher {

    public static void teach(Network network, DataSet dataSet, double learningRate, double errorThreshold) {
        List<String> decisions = dataSet.getDecisions();
        double error = Double.MAX_VALUE;
        int counter = 0;
        while (!(error < errorThreshold)) {
            counter++;
            error = 0.0;
            for (int i = 0; i < dataSet.getDataPoints().size(); i++) {
                String d = decisions.get(i);
                error += network.learn(dataSet.getDataPoints().get(i), d, learningRate);
            }
            if (counter % 1000 == 0) {
                System.out.println("Epochs passed: " + counter + ", error: " + error);
            }
        }
    }

    public static void optimizeByErrorThreshold(Network network, DataSet dataSet, double learningRate, double errorThreshold) {
        List<String> decisions = dataSet.getDecisions();
        double error = Double.MAX_VALUE;
        double previousError = Double.MAX_VALUE;
        int counter = 0;
        while (!(error < errorThreshold) || previousError > error) {
            counter++;
            previousError = error;
            error = 0.0;
            for (int i = 0; i < dataSet.getDataPoints().size(); i++) {
                String d = decisions.get(i);
                error += network.learn(dataSet.getDataPoints().get(i), d, learningRate);
            }
            if (counter % 1000 == 0) {
                System.out.println("Epochs passed: " + counter + ", error: " + error);
            }
        }
    }
}
