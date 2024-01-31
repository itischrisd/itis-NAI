package perceptron.activation;

public class SigmoidUnipolar implements ActivationFunction {

    @Override
    public double calculate(double net) {
        return 1 / (1 + Math.exp(-net));
    }

    @Override
    public double derivative(double y) {
        return - y * (1 - y);
    }

    @Override
    public double activeValue() {
        return 1;
    }

    @Override
    public double inactiveValue() {
        return 0;
    }
}
