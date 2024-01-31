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
}
