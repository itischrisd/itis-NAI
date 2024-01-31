package perceptron.activation;

public class SigmoidBipolar implements ActivationFunction {

    @Override
    public double calculate(double net) {
        return (2 / (1 + Math.exp(-net))) - 1;
    }

    @Override
    public double derivative(double y) {
        return - 0.5 * (1 - Math.pow(y, 2));
    }
}
