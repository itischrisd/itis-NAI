package perceptron;

import generic.Algebra;

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

    public Perceptron(List<Double> weigths, Double threshhold) {
        this.weigths = weigths;
        this.threshhold = threshhold;
    }

    public List<Double> getWeigths() {
        return weigths;
    }

    public Number getThreshhold() {
        return threshhold;
    }

    public int calcualte(List<Double> vector) {
        double net = Algebra.dotProduct(vector, weigths);
        if (net < threshhold)
            return 0;
        else
            return 1;
    }

    public int learn(List<Double> vector, int d, double alfa, double beta) {
        int y = calcualte(vector);
        modifyWeigths(vector, d, y, alfa);
        modifyThreshold(d, y, beta);
        return y;
    }

    private void modifyWeigths(List<Double> vector, int d, int y, double alfa) {
        List<Double> newWeights = new ArrayList<>();
        for (int i = 0; i < weigths.size(); i++) {
            newWeights.add(weigths.get(i) + (d - y) * alfa * vector.get(i));
        }
        weigths = Algebra.normalize(newWeights);
    }

    private void modifyThreshold(int d, int y, double beta) {
        threshhold = threshhold - (d - y) * beta;
    }

    @Override
    public String toString() {
        return "weigths = " + weigths + ", threshhold = " + threshhold;
    }
}
