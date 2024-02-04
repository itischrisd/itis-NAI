package perceptron;

import generic.NumericDataSet;

public class Teacher {

    public static void teach(Perceptron perceptron, NumericDataSet numericDataSet, String classValue) {
        boolean complete = false;
        int counter = 1;
        while (!complete) {
            complete = true;
            for (int i = 0; i < numericDataSet.getAllDataPoints().size(); i++) {
                int d = classValue.equals(numericDataSet.getAllDecisions().get(i)) ? 1 : 0;
                double y = perceptron.learn(numericDataSet.getAllDataPoints().get(i), d, 0.0001);
                complete = complete && d == y;
            }
            System.out.println("Minęło epok: " + counter++);
            if (counter > 10000)
                break;
        }
    }
}
