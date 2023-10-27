import generic.DataSet;
import generic.FileSelector;
import knn.NearestNeighbors;
import perceptron.Perceptron;
import perceptron.Teacher;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {

        String filePath = FileSelector.getFilePath("data/iris.csv");
        DataSet dataSet = DataSet.parseCSV(filePath);
//        Perceptron perceptron = new Perceptron(dataSet.getAttributeNames().size());
//        Teacher.teach(perceptron, dataSet, "Setosa");
        List<Double> dataPoint = new ArrayList<>();
//        7,3.2,4.7,1.4
        dataPoint.add(7.0);
        dataPoint.add(3.2);
        dataPoint.add(4.7);
        dataPoint.add(1.4);
        NearestNeighbors.calculate(7, dataSet, dataPoint);
    }
}