package perceptron.activation;

public interface ActivationFunction {

    double calculate(double net);

    double derivative(double y);

    double activeValue();

    double inactiveValue();
}
