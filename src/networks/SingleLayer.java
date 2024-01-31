package networks;

import perceptron.Perceptron;
import perceptron.activation.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class SingleLayer {

    public final List<Perceptron> perceptrons;
    private final List<String> classes;

    public SingleLayer(ActivationFunction activationFunction, List<String> classes, int numberOfInputs) {
        this.perceptrons = new ArrayList<>();
        this.classes = classes;
        for (int i = 0; i < classes.size(); i++) {
            perceptrons.add(new Perceptron(activationFunction, numberOfInputs));
        }
    }

    public String calculate(List<Double> inputs) {
        List<Double> outputs = new ArrayList<>();
        for (Perceptron perceptron : perceptrons) {
            outputs.add(perceptron.calcualte(inputs));
        }
        int maxIndex = 0;
        for (int i = 0; i < outputs.size(); i++) {
            if (outputs.get(i) > outputs.get(maxIndex)) {
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
        for (int i = 0; i < perceptrons.size(); i++) {
            perceptrons.get(i).learn(inputs, desiredOutputs.get(i), alfa);
        }
        return calculate(inputs);
    }
}
