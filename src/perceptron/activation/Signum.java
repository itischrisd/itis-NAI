package perceptron.activation;

public class Signum implements ActivationFunction {

    @Override
    public double calculate(double net) {
        return net >= 0 ? 1 : -1;
    }

    @Override
    public double derivative(double y) {
        return -1.0;
    }

    @Override
    public double activeValue() {
        return 1;
    }

    @Override
    public double inactiveValue() {
        return -1;
    }
}
