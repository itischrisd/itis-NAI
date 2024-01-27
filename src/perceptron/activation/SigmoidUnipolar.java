package perceptron.activation;

import generic.Algebra;

import java.util.List;

public class SigmoidUnipolar implements ActivationFunction {

    @Override
    public double calculate(List<Double> weights, List<Double> inputs) {
        double net = Algebra.dotProduct(weights, inputs);
        return 1 / (1 + Math.exp(-net));
    }

    @Override
    public double derivative(double y) {
        return y * (1 - y);
    }
}
