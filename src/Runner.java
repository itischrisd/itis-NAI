import generic.DataSet;
import generic.FileSelector;
import perceptron.Perceptron;
import perceptron.Teacher;

public class Runner {
    public static void main(String[] args) {

        String filePath = FileSelector.getFilePath("data/iris.csv");
        DataSet dataSet = DataSet.parseCSV(filePath);
        Perceptron perceptron = new Perceptron(dataSet.getAttributeNames().size());
        Teacher.teach(perceptron, dataSet, "Setosa");
    }
}