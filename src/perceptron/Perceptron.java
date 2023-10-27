package perceptron;

import java.util.ArrayList;
import java.util.List;

public class Perceptron {

    private List<Double> weigths;
    private Double threshhold;

    public Perceptron(int size) {
        weigths = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            weigths.add(1.0);
        }
        threshhold = 0.0;
    }

    public int calcualte(List<Double> vector) {
        Double net = dotProduct(vector);
        if (net < threshhold)
            return 0;
        else
            return 1;
    }

    public int learn(List<Double> vector, int d) {
        int y = calcualte(vector);
        modifyWeigths(vector, d, y);
        modifyThreshold(d, y);
        return y;
    }

    private void modifyWeigths(List<Double> vector, int d, int y) {
        double alfa = 0.0001;
        List<Double> newWeights = new ArrayList<>();
        for (int i = 0; i < weigths.size(); i++) {
            newWeights.add(weigths.get(i) + (d - y) * alfa * vector.get(i));
        }
        weigths = normlize(newWeights);
    }

    private List<Double> normlize(List<Double> newWeights) {
        List<Double> newWeightsNormalized = new ArrayList<>();
        double sumOfSqueares = 0.0;
        for (Double weight : newWeights)
            sumOfSqueares += weight * weight;
        double length = Math.sqrt(sumOfSqueares);
        for (Double weight : newWeights)
            newWeightsNormalized.add(weight / length);
        return newWeightsNormalized;
    }

    private void modifyThreshold(int d, int y) {
        double beta = 0.0001;
        threshhold = threshhold - (d - y) * beta;
    }

    private Double dotProduct(List<Double> vector) {
        double net = 0.0;
        for (int i = 0; i < vector.size(); i++) {
            net += vector.get(i) * weigths.get(i);
        }
        return net;
    }

    @Override
    public String toString() {
        return "weigths = " + weigths + ", threshhold = " + threshhold;
    }
}
