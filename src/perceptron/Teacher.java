package perceptron;

import generic.DataSet;

public class Teacher {

    public static void teach(Perceptron perceptron, DataSet dataSet, String classValue) {
        boolean complete = false;
        int counter = 1;
        while (!complete) {
            complete = true;
            for (int i = 0; i < dataSet.getDataPoints().size(); i++) {
                int d = classValue.equals(dataSet.getDecisions().get(i)) ? 1 : 0;
                double y = perceptron.learn(dataSet.getDataPoints().get(i), d, 0.0001);
                complete = complete && d == y;
            }
            System.out.println("Epochs passed: " + counter++);
            if (counter > 10000)
                break;
        }
    }
}
