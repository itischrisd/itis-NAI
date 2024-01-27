package perceptron;

import generic.Algebra;
import perceptron.activation.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class Perceptron {

    private final ActivationFunction activationFunction;
    private List<Double> weigths;

    public Perceptron(ActivationFunction activationFunction, int size) {
        this.activationFunction = activationFunction;
        weigths = new ArrayList<>();
        for (int i = 0; i < size + 1; i++) {
            weigths.add(1.0);
        }
    }

    public Perceptron(ActivationFunction activationFunction, List<Double> weigths, Double threshhold) {
        this.activationFunction = activationFunction;
        this.weigths = weigths;
        weigths.add(threshhold);
    }

    public double calcualte(List<Double> inputs) {
        List<Double> inputsWithThreshold = new ArrayList<>(inputs);
        inputsWithThreshold.add(-1.0);
        return activationFunction.calculate(weigths, inputsWithThreshold);
    }

    public double learn(List<Double> inputs, int d, double alfa) {
        double y = calcualte(inputs);
        List<Double> inputsWithThreshold = new ArrayList<>(inputs);
        inputsWithThreshold.add(-1.0);
        List<Double> shift = Algebra.multiplyVectorByScalar(inputsWithThreshold, - (d - y) * activationFunction.derivative(y) * alfa);
        this.weigths = Algebra.addVectors(weigths, shift);
        this.weigths = Algebra.normalize(weigths);
        return y;
    }

    @Override
    public String toString() {
        return "weigths first, last is threshold = " + weigths;
    }
}
