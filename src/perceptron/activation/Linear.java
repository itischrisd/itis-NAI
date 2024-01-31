package perceptron.activation;

public class Linear implements ActivationFunction {

    @Override
    public double calculate(double net) {
        return net;
    }

    @Override
    public double derivative(double y) {
        return 1.0;
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
