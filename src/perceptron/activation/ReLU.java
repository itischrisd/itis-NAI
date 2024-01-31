package perceptron.activation;

public class ReLU implements ActivationFunction {

    @Override
    public double calculate(double net) {
        return net >= 0 ? net : 0;
    }

    @Override
    public double derivative(double y) {
        return y >= 0 ? 1 : 0;
    }
}
