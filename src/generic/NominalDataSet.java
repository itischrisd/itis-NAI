package generic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NominalDataSet {

    private final List<String> attributeNames;
    private final List<List<String>> dataPoints;
    private final List<String> decisions;

    private NominalDataSet(List<String> attributeNames, List<List<String>> dataPoints, List<String> decisions) {
        this.attributeNames = attributeNames;
        this.dataPoints = dataPoints;
        this.decisions = decisions;
    }

    public static NominalDataSet parseCSV(String filePath) {
        List<String> attributeNames = new ArrayList<>();
        List<List<String>> dataPoints = new ArrayList<>();
        List<String> decisions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            List<String> items = new ArrayList<>(List.of(line.split(",")));
            List<String> dataPoint;

            attributeNames.addAll(items);
            attributeNames.removeLast();

            while ((line = reader.readLine()) != null) {
                items = new ArrayList<>(List.of(line.split(",")));
                dataPoint = new ArrayList<>();
                for (int i = 0; i < items.size() - 1; i++) {
                    dataPoint.add(items.get(i));
                }
                dataPoints.add(List.copyOf(dataPoint));
                decisions.add(items.getLast().replace("\"", ""));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new NominalDataSet(attributeNames, dataPoints, decisions);
    }

    public List<String> getAttributeNames() {
        return attributeNames;
    }

    public List<List<String>> getDataPoints() {
        return dataPoints;
    }

    public List<String> getDecisions() {
        return decisions;
    }

    public void print() {
        if (attributeNames != null) {
            for (String attributeName : attributeNames) {
                System.out.print(attributeName + "\t");
            }
            System.out.println("class");
        } else {
            for (int i = 0; i < dataPoints.getFirst().size(); i++) {
                System.out.print("attrivute" + (i + 1) + "\t");
            }
            System.out.println("class");
        }

        for (int i = 0; i < dataPoints.size(); i++) {
            for (String attribute : dataPoints.get(i))
                System.out.print(attribute + "\t");
            System.out.println(decisions.get(i));
        }

        System.out.println("Total data points: " + dataPoints.size());
    }
}
