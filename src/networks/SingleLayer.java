package networks;

import generic.Algebra;
import perceptron.activation.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class SingleLayer extends Network {

    private final ActivationFunction activationFunction;
    private final List<List<Double>> weightMatrix;

    public SingleLayer(ActivationFunction activationFunction, List<String> classes, int numberOfInputs) {
        this.activationFunction = activationFunction;
        super.classes = classes;
        weightMatrix = new ArrayList<>();
        for (int i = 0; i < classes.size(); i++) {
            List<Double> weightVector = new ArrayList<>();
            for (int j = 0; j < numberOfInputs + 1; j++) {
                weightVector.add(Math.random());
            }
            weightMatrix.add(weightVector);
        }
    }

    public List<Double> calculate(List<Double> inputs) {
        List<Double> inputsWithThreshold = new ArrayList<>(inputs);
        inputsWithThreshold.add(-1.0);
        List<Double> outputs = new ArrayList<>();
        List<Double> nets = new ArrayList<>();
        for (List<Double> weightVector : weightMatrix) {
            nets.add(Algebra.dotProduct(weightVector, inputsWithThreshold));
        }
        for (Double net : nets) {
            outputs.add(activationFunction.calculate(net));
        }
        return outputs;
    }

    @Override
    public String decide(List<Double> inputs) {
        List<Double> outputs = calculate(inputs);
        int maxIndex = 0;
        for (int i = 0; i < outputs.size(); i++) {
            if (outputs.get(i) > outputs.get(maxIndex)) {
                maxIndex = i;
            }
        }
        return classes.get(maxIndex);
    }

    @Override
    public double learn(List<Double> inputs, String d, double eta) {
        List<Double> outputs = calculate(inputs);
        List<Double> inputsWithThreshold = new ArrayList<>(inputs);
        inputsWithThreshold.add(-1.0);
        List<Double> desiredOutputs = new ArrayList<>();
        for (String aClass : classes) {
            desiredOutputs.add(aClass.equals(d) ? activationFunction.activeValue() : activationFunction.inactiveValue());
        }
        double error = 0.0;
        for (int i = 0; i < outputs.size(); i++) {
            error += Math.pow(desiredOutputs.get(i) - outputs.get(i), 2);
        }
        error /= 2.0;
        List<Double> deltaVector = Algebra.subtractVectors(desiredOutputs, outputs);
        for (int i = 0; i < deltaVector.size(); i++) {
            deltaVector.set(i, (-1) * deltaVector.get(i) * activationFunction.derivative(outputs.get(i) * eta));
        }
        List<List<Double>> shifts = new ArrayList<>();
        for (int i = 0; i < weightMatrix.size(); i++) {
            shifts.add(Algebra.multiplyVectorByScalar(inputsWithThreshold, deltaVector.get(i)));
        }
        for (int i = 0; i < weightMatrix.size(); i++) {
            weightMatrix.set(i, Algebra.addVectors(weightMatrix.get(i), shifts.get(i)));
        }
        return error;
    }
}
