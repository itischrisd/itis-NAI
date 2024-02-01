package networks;

import generic.DataSet;

import java.util.List;

public class NetworkTeacher {

    public static void teach(SingleLayer singleLayer, DataSet dataSet) {
        List<String> decisions = dataSet.getDecisions();
        boolean complete = false;
        int counter = 1;
        while (!complete) {
            complete = true;
            for (int i = 0; i < dataSet.getDataPoints().size(); i++) {
                String d = decisions.get(i);
                String y = singleLayer.learn(dataSet.getDataPoints().get(i), d, 0.001);
                complete = complete && d.equals(y);
            }
            System.out.println("Epochs passed: " + counter++);
            if (counter > 10000)
                break;
        }
    }

    public static void teach(MultiLayer multiLayer, DataSet dataSet) {
        List<String> decisions = dataSet.getDecisions();
        boolean complete = false;
        int counter = 1;
        while (!complete) {
            complete = true;
            for (int i = 0; i < dataSet.getDataPoints().size(); i++) {
                String d = decisions.get(i);
                String y = multiLayer.learn(dataSet.getDataPoints().get(i), d, 0.001);
                complete = complete && d.equals(y);
            }
            System.out.println("Epochs passed: " + counter++);
            if (counter > 10000) {
                break;
            }
        }
    }
}
