import generic.DataSet;
import generic.FileSelector;
import knn.NearestNeighbors;
import perceptron.Perceptron;
import perceptron.Teacher;

import java.util.Arrays;
import java.util.List;

public class Runner {
    public static void main(String[] args) {

        String filePath = FileSelector.getFilePath("data/iris.csv");
        DataSet dataSet = DataSet.parseCSV(filePath);

        List<Double> testDataPoint = Arrays.asList(4.8, 3.0, 1.4, 0.1);
        NearestNeighbors.calculate(7, dataSet, testDataPoint);

        Perceptron perceptron = new Perceptron(dataSet.getAttributeNames().size());
        Teacher.teach(perceptron, dataSet, "Setosa");
        int result = perceptron.calcualte(testDataPoint);
        System.out.println("Perceptron prediction: " + result);
    }
}