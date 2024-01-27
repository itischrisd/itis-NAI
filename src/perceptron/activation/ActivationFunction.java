package perceptron.activation;

import java.util.List;

public interface ActivationFunction {

    double calculate(List<Double> weights, List<Double> inputs);

    double derivative(double net);
}
