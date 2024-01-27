package perceptron.activation;

import generic.Algebra;

import java.util.List;

public class StepBipolar implements ActivationFunction {

    @Override
    public double calculate(List<Double> weights, List<Double> inputs) {
        return Algebra.dotProduct(weights, inputs) >= 0 ? 1 : -1;
    }

    @Override
    public double derivative(double y) {
        return -1.0;
    }
}
