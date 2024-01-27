package perceptron.activation;

import generic.Algebra;

import java.util.List;

public class ReLU implements ActivationFunction {

    @Override
    public double calculate(List<Double> weights, List<Double> inputs) {
        double net = Algebra.dotProduct(weights, inputs);
        return net >= 0 ? net : 0;
    }

    @Override
    public double derivative(double y) {
        return y >= 0 ? 1 : 0;
    }
}
