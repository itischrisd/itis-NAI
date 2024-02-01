package networks;

import generic.Algebra;
import perceptron.activation.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class MultiLayer extends Network {

    public final List<List<List<Double>>> weightsOfLayers;
    private final List<ActivationFunction> activationFunctions;

    public MultiLayer(List<ActivationFunction> activationFunctions, List<String> classes, int numberOfInputs) {
        this.activationFunctions = activationFunctions;
        this.weightsOfLayers = new ArrayList<>();
        super.classes = classes;
        for (int i = 0; i < activationFunctions.size() - 1; i++) {
            weightsOfLayers.add(createWeightMatrix(numberOfInputs + 1, numberOfInputs + 1));
        }
        weightsOfLayers.add(createWeightMatrix(numberOfInputs + 1, classes.size()));
    }

    public List<List<Double>> calculate(List<Double> inputs) {
        List<Double> layerInput = new ArrayList<>(inputs);
        layerInput.add(-1.0);
        List<List<Double>> outputs = new ArrayList<>();

        for (int i = 0; i < weightsOfLayers.size(); i++) {
            List<List<Double>> weightMatrix = weightsOfLayers.get(i);
            List<Double> nets = Algebra.multiplyMatrixByVector(weightMatrix, layerInput);
            List<Double> layerOutput = Algebra.applyActivationFunction(nets, activationFunctions.get(i));
            outputs.add(layerOutput);
            layerInput = new ArrayList<>(layerOutput);
            layerInput.add(-1.0);
        }

        return outputs;
    }

    @Override
    public String decide(List<Double> inputs) {
        List<List<Double>> outputs = calculate(inputs);
        int maxIndex = 0;

        for (int i = 0; i < outputs.getLast().size(); i++) {
            if (outputs.getLast().get(i) > outputs.getLast().get(maxIndex)) {
                maxIndex = i;
            }
        }

        return classes.get(maxIndex);
    }

    @Override
    public double learn(List<Double> inputs, String d, double eta) {
        inputs = new ArrayList<>(inputs);
        inputs.add(-1.0);
        List<List<Double>> outputs = new ArrayList<>(calculate(inputs));
        List<Double> desiredOutputs = getDesiredOutputVector(d);
        double error = 0.0;
        for (int i = 0; i < outputs.getLast().size(); i++) {
            error += Math.pow(desiredOutputs.get(i) - outputs.getLast().get(i), 2);
        }
        error /= 2.0;
        List<List<Double>> deltas = new ArrayList<>();

        deltas.add(calculateLastDelta(desiredOutputs, outputs.getLast()));
        List<Double> nextLayerDeltas = new ArrayList<>(deltas.getLast());
        List<List<Double>> nextLayerWeights = new ArrayList<>(weightsOfLayers.getLast());

        for (int i = weightsOfLayers.size() - 2; i >= 0; i--) {
            List<Double> derivativeVector = Algebra.applyDerivative(outputs.get(i), activationFunctions.get(i));
            derivativeVector = Algebra.multiplyVectorByScalar(derivativeVector, -1.0);
            List<Double> backpropagationDelta = getBackpropagationDelta(nextLayerWeights, nextLayerDeltas);
            List<Double> deltaVector = Algebra.multiplyVectorElements(backpropagationDelta, derivativeVector);
            deltas.addFirst(deltaVector);
            nextLayerDeltas = new ArrayList<>(deltaVector);
            nextLayerWeights = new ArrayList<>(weightsOfLayers.get(i));
        }

        List<List<List<Double>>> shifts = new ArrayList<>();
        for (int i = 0; i < weightsOfLayers.size(); i++) {
            List<List<Double>> shiftMatrix = new ArrayList<>();
            if (i == 0) {
                for (int j = 0; j < weightsOfLayers.get(i).size(); j++) {
                    List<Double> shiftVector = Algebra.multiplyVectorByScalar(inputs, deltas.get(i).get(j) * eta);
                    shiftMatrix.add(shiftVector);
                }
            } else {
                for (int j = 0; j < weightsOfLayers.get(i).size(); j++) {
                    List<Double> shiftVector = Algebra.multiplyVectorByScalar(outputs.get(i - 1), deltas.get(i).get(j) * eta);
                    shiftMatrix.add(shiftVector);
                }
            }
            shifts.add(shiftMatrix);
        }

        for (int i = 0; i < weightsOfLayers.size(); i++) {
            List<List<Double>> weightMatrix = weightsOfLayers.get(i);
            List<List<Double>> shiftMatrix = shifts.get(i);
            for (int j = 0; j < weightMatrix.size(); j++) {
                List<Double> weightVector = weightMatrix.get(j);
                List<Double> shiftVector = shiftMatrix.get(j);
                weightMatrix.set(j, Algebra.addVectors(weightVector, shiftVector));
            }
        }

        for (int i = 0; i < weightsOfLayers.size(); i++) {
            for (int j = 0; j < weightsOfLayers.get(i).size(); j++) {
                weightsOfLayers.get(i).set(j, Algebra.normalize(weightsOfLayers.get(i).get(j)));
            }
        }

        return error;
    }

    private List<Double> getBackpropagationDelta(List<List<Double>> nextLayerWeights, List<Double> nextLayerDeltas) {
        List<Double> deltaVector = new ArrayList<>();
        for (int i = 0; i < nextLayerWeights.getFirst().size(); i++) {
            double sum = 0.0;
            for (int j = 0; j < nextLayerDeltas.size(); j++) {
                sum += nextLayerWeights.get(j).get(i) * nextLayerDeltas.get(j);
            }
            deltaVector.add(sum);
        }
        return deltaVector;
    }


    private List<Double> getDesiredOutputVector(String d) {
        List<Double> desiredOutputs = new ArrayList<>();
        for (String aClass : classes) {
            desiredOutputs.add(aClass.equals(d) ? activationFunctions.getLast().activeValue() : activationFunctions.getLast().inactiveValue());
        }
        return desiredOutputs;
    }

    private List<Double> calculateLastDelta(List<Double> desiredOutputs, List<Double> actualOutputs) {
        List<Double> outputDifferenceVector = Algebra.subtractVectors(desiredOutputs, actualOutputs);
        List<Double> derivativeVector = Algebra.applyDerivative(actualOutputs, activationFunctions.getLast());
        derivativeVector = Algebra.multiplyVectorByScalar(derivativeVector, -1.0);
        return Algebra.multiplyVectorElements(outputDifferenceVector, derivativeVector);
    }

    private List<List<Double>> createWeightMatrix(int inputCount, int outputCount) {
        List<List<Double>> weightMatrix = new ArrayList<>();
        for (int i = 0; i < outputCount; i++) {
            List<Double> weightVector = new ArrayList<>();
            for (int j = 0; j < inputCount; j++) {
                weightVector.add(Math.random());
            }
            weightMatrix.add(weightVector);
        }
        return weightMatrix;
    }
}
