import generic.DataSet;
import generic.FileSelector;
import knn.NearestNeighbors;
import perceptron.Perceptron;
import perceptron.Teacher;
import perceptron.activation.StepUnipolar;

import java.util.Arrays;
import java.util.List;

public class ModelRunner {
    public static void main(String[] args) {

        String filePath = FileSelector.getFilePath("data/iris.csv");
        DataSet dataSet = DataSet.parseCSV(filePath);

        List<Double> testDataPoint = Arrays.asList(5.7, 4.4, 1.5, .4);
        NearestNeighbors.calculate(7, dataSet, testDataPoint);

        Perceptron perceptron = new Perceptron(new StepUnipolar(), dataSet.getAttributeNames().size());
        Teacher.teach(perceptron, dataSet, "Setosa");
        double result = perceptron.calcualte(testDataPoint);
        System.out.println("Perceptron prediction: " + result);
        System.out.println(perceptron);
    }
}