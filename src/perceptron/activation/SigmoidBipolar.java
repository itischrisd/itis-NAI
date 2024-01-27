package perceptron.activation;

import generic.Algebra;

import java.util.List;

public class SigmoidBipolar implements ActivationFunction {

    @Override
    public double calculate(List<Double> weights, List<Double> inputs) {
        double net = Algebra.dotProduct(weights, inputs);
        return (2 / (1 + Math.exp(-net))) - 1;
    }

    @Override
    public double derivative(double y) {
        return 0.5 * (1 - Math.pow(y, 2));
    }
}
