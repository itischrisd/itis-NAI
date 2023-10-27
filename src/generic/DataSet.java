package generic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSet {

    private final List<String> attributeNames;
    private final List<List<Double>> dataPoints;
    private final List<String> decisions;

    private DataSet(String filePath) {
        this.attributeNames = new ArrayList<>();
        this.dataPoints = new ArrayList<>();
        this.decisions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            List<String> items = new ArrayList<>(List.of(line.split(",")));
            List<Double> dataPoint = new ArrayList<>();

            if (items.get(0).chars().anyMatch(Character::isAlphabetic)) {
                attributeNames.addAll(items);
                attributeNames.removeLast();
            } else {
                for (int i = 0; i < items.size() - 1; i++) {
                    attributeNames.add(String.valueOf(i + 1));
                    dataPoint.add(Double.valueOf(items.get(i)));
                }
                dataPoints.add(List.copyOf(dataPoint));
                decisions.add(items.getLast().replace("\"", ""));
            }

            while ((line = reader.readLine()) != null) {
                items = new ArrayList<>(List.of(line.split(",")));
                dataPoint = new ArrayList<>();
                for (int i = 0; i < items.size() - 1; i++) {
                    dataPoint.add(Double.valueOf(items.get(i)));
                }
                dataPoints.add(List.copyOf(dataPoint));
                decisions.add(items.getLast().replace("\"", ""));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataSet parseCSV(String filePath) {
        return new DataSet(filePath);
    }

    public List<String> getAttributeNames() {
        return attributeNames;
    }

    public List<List<Double>> getDataPoints() {
        return dataPoints;
    }

    public List<String> getDecisions() {
        return decisions;
    }

    public void print() {
        for(String attributeName : attributeNames)
            System.out.print(attributeName + "\t");
        System.out.println("class");

        for (int i = 0; i < dataPoints.size(); i++) {
            for(Double attribute : dataPoints.get(i))
                System.out.print(attribute + "\t");
            System.out.println(decisions.get(i));
        }

        System.out.println("Total data points: " + dataPoints.size());
    }
}
