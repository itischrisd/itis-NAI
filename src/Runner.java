import generic.DataSet;
import perceptron.Perceptron;
import perceptron.Teacher;

public class Runner {
    public static void main(String[] args) {

        DataSet dataSet = DataSet.parseCSV("iris.csv");
        Perceptron perceptron = new Perceptron(dataSet.getAttributeNames().size());
        Teacher.teach(perceptron, dataSet, "Virginica");
    }
}