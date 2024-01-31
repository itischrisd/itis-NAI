package networks;

import perceptron.Perceptron;
import perceptron.activation.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class MultiLayer {

    private final List<SingleLayer> layers;
    private final List<String> classes;

    public MultiLayer(List<ActivationFunction> activationFunctions, int numberOfLayers, List<String> classes, int numberOfInputs) {
        this.layers = new ArrayList<>();
        this.classes = classes;
        for (int i = 0; i < numberOfLayers; i++) {
            layers.add(new SingleLayer(activationFunctions.get(i), classes, numberOfInputs));
        }
    }

    public List<List<Double>> calculate(List<Double> inputs) {
        List<List<Double>> outputs = new ArrayList<>();
        outputs.add(layers.getFirst().calculate(inputs));
        for (int i = 1; i < layers.size(); i++) {
            outputs.add(layers.get(i).calculate(outputs.get(i - 1)));
        }
        return outputs;
    }

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

    public String learn(List<Double> inputs, String d, double alfa) {
        List<Double> desiredOutputs = new ArrayList<>();
        for (String aClass : classes) {
            desiredOutputs.add(aClass.equals(d) ? 1.0 : 0.0);
        }
        return "";
    }
}
