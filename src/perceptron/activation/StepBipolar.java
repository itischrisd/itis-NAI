package perceptron.activation;

public class StepBipolar implements ActivationFunction {

    @Override
    public double calculate(double net) {
        return net >= 0 ? 1 : -1;
    }

    @Override
    public double derivative(double y) {
        return -1.0;
    }
}
