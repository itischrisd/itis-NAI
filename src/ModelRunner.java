import generic.DataSet;
import generic.FileSelector;
import knn.NearestNeighbors;
import networks.MultiLayer;
import networks.NetworkTeacher;
import networks.SingleLayer;
import perceptron.Perceptron;
import perceptron.Teacher;
import perceptron.activation.ReLU;
import perceptron.activation.SigmoidBipolar;
import perceptron.activation.SigmoidUnipolar;
import perceptron.activation.StepUnipolar;

import java.util.Arrays;
import java.util.List;

public class ModelRunner {
    public static void main(String[] args) {

        String filePath = FileSelector.getFilePath("data/iris.csv");
        DataSet dataSet = DataSet.parseCSV(filePath);

//        List<Double> testDataPoint = Arrays.asList(5.7, 4.4, 1.5, .4);
//        NearestNeighbors.calculate(7, dataSet, testDataPoint);
//
//        Perceptron perceptron = new Perceptron(new StepUnipolar(), dataSet.getAttributeNames().size());
//        Teacher.teach(perceptron, dataSet, "Setosa");
//        double result = perceptron.calcualte(testDataPoint);
//        System.out.println("Perceptron prediction: " + result);
//        System.out.println(perceptron);

        List<String> classes = Arrays.asList("Setosa", "Virginica", "Versicolor");

//        SingleLayer singleLayer = new SingleLayer(new SigmoidUnipolar(), classes, dataSet.getAttributeNames().size());
//        NetworkTeacher.teach(singleLayer, dataSet);


//        List<Double> setosa = Arrays.asList(5.7, 4.4, 1.5, 0.4);
//        List<Double> virginica = Arrays.asList(6.7, 3.3, 5.7, 2.5);
//        List<Double> versicolor = Arrays.asList(4.9, 2.4, 3.3, 1.0);
//        System.out.println("Single layer prediction for Setosa: " + singleLayer.decide(setosa));
//        System.out.println("Single layer prediction for Virginica: " + singleLayer.decide(virginica));
//        System.out.println("Single layer prediction for Versicolor: " + singleLayer.decide(versicolor));

        MultiLayer multiLayer = new MultiLayer(Arrays.asList(new StepUnipolar(), new StepUnipolar(), new SigmoidUnipolar()), classes, dataSet.getAttributeNames().size());
        NetworkTeacher.teach(multiLayer, dataSet, 0.1, 0.5);


        List<Double> setosa = Arrays.asList(5.7, 4.4, 1.5, 0.4);
        List<Double> virginica = Arrays.asList(6.7, 3.3, 5.7, 2.5);
        List<Double> versicolor = Arrays.asList(4.9, 2.4, 3.3, 1.0);
        System.out.println("Single layer prediction for Setosa: " + multiLayer.decide(setosa));
        System.out.println("Single layer prediction for Virginica: " + multiLayer.decide(virginica));
        System.out.println("Single layer prediction for Versicolor: " + multiLayer.decide(versicolor));
    }
}