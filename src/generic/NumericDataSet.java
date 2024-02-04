package generic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NumericDataSet {

    private final List<String> attributeNames;
    private final List<List<Double>> allDataPoints;
    private final List<String> allDecisions;
    private List<List<Double>> trainDataPoints;
    private List<String> trainDecisions;
    private List<List<Double>> testDataPoints;
    private List<String> testDecisions;
    private List<List<List<Double>>> crossValidationDataPoints;
    private List<List<String>> crossValidationDecisions;

    public NumericDataSet(List<List<Double>> dataPoints, List<String> decisions) {
        this.attributeNames = null;
        this.allDataPoints = dataPoints;
        this.allDecisions = decisions;
    }

    public NumericDataSet(List<String> attributeNames, List<List<Double>> dataPoints, List<String> decisions) {
        this.attributeNames = attributeNames;
        this.allDataPoints = dataPoints;
        this.allDecisions = decisions;
    }

    public static NumericDataSet parseCSV(String filePath) {
        List<String> attributeNames = new ArrayList<>();
        List<List<Double>> dataPoints = new ArrayList<>();
        List<String> decisions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            List<String> items = new ArrayList<>(List.of(line.split(",")));
            List<Double> dataPoint = new ArrayList<>();

            if (items.getFirst().chars().anyMatch(Character::isAlphabetic)) {
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

        return new NumericDataSet(attributeNames, dataPoints, decisions);
    }

    public List<String> getAttributeNames() {
        return attributeNames;
    }

    public List<List<Double>> getAllDataPoints() {
        return allDataPoints;
    }

    public List<String> getAllDecisions() {
        return allDecisions;
    }

    public void splitToTrainAndTest(double ratio) {
        trainDataPoints = new ArrayList<>();
        trainDecisions = new ArrayList<>();
        testDataPoints = new ArrayList<>();
        testDecisions = new ArrayList<>();

        Map<String, List<Integer>> decisionGroups = new HashMap<>();
        for (int i = 0; i < allDecisions.size(); i++) {
            String decision = allDecisions.get(i);
            decisionGroups.putIfAbsent(decision, new ArrayList<>());
            decisionGroups.get(decision).add(i);
        }

        for (Map.Entry<String, List<Integer>> entry : decisionGroups.entrySet()) {
            List<Integer> indexes = entry.getValue();
            Collections.shuffle(indexes);

            int splitPoint = (int) (indexes.size() * ratio);

            for (int i = 0; i < splitPoint; i++) {
                int index = indexes.get(i);
                trainDataPoints.add(allDataPoints.get(index));
                trainDecisions.add(allDecisions.get(index));
            }

            for (int i = splitPoint; i < indexes.size(); i++) {
                int index = indexes.get(i);
                testDataPoints.add(allDataPoints.get(index));
                testDecisions.add(allDecisions.get(index));
            }
        }
    }

    public void splitForCrossValidation(int n) {
        int totalSize = trainDataPoints.size();
        int chunkSize = totalSize / n;
        int remainder = totalSize % n;

        crossValidationDataPoints = new ArrayList<>();
        crossValidationDecisions = new ArrayList<>();

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < totalSize; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        int start = 0;
        for (int i = 0; i < n; i++) {
            int end = start + chunkSize + (remainder > i ? 1 : 0);

            List<List<Double>> chunkDataPoints = new ArrayList<>();
            List<String> chunkDecisions = new ArrayList<>();
            for (int j = start; j < end; j++) {
                int index = indices.get(j);
                chunkDataPoints.add(new ArrayList<>(trainDataPoints.get(index)));
                chunkDecisions.add(trainDecisions.get(index));
            }
            crossValidationDataPoints.add(chunkDataPoints);
            crossValidationDecisions.add(chunkDecisions);

            start = end;
        }
    }

    public void print() {
        assert attributeNames != null;
        for (String attributeName : attributeNames) {
            System.out.print(attributeName + "\t");
        }
        System.out.println("class");

        for (int i = 0; i < allDataPoints.size(); i++) {
            for (Double attribute : allDataPoints.get(i))
                System.out.print(attribute + "\t");
            System.out.println(allDecisions.get(i));
        }

        System.out.println("Total data points: " + allDataPoints.size());
    }

    public List<List<Double>> getTrainDataPoints() {
        return trainDataPoints;
    }

    public List<String> getTrainDecisions() {
        return trainDecisions;
    }

    public List<List<Double>> getTestDataPoints() {
        return testDataPoints;
    }

    public List<String> getTestDecisions() {
        return testDecisions;
    }

    public List<List<List<Double>>> getCrossValidationDataPoints() {
        return crossValidationDataPoints;
    }

    public List<List<String>> getCrossValidationDecisions() {
        return crossValidationDecisions;
    }
}
