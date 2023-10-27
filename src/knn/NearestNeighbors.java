package knn;

import generic.DataSet;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NearestNeighbors {

    public static void calculate(int k, DataSet dataSet, List<Double> dataPoint) {
        Map<Integer, Double> nearestNeighbors = new HashMap<>();

        for (int i = 0; i < dataSet.getDataPoints().size(); i++) {
            double distance = calculateDistance(dataPoint, dataSet.getDataPoints().get(i));

            if (nearestNeighbors.size() < k) {
                nearestNeighbors.put(i, distance);
            } else {
                int farthestNeighbor = Collections.max(nearestNeighbors.entrySet(), Map.Entry.comparingByValue()).getKey();
                if (distance < nearestNeighbors.get(farthestNeighbor)) {
                    nearestNeighbors.remove(farthestNeighbor);
                    nearestNeighbors.put(i, distance);
                }
            }
        }

        List<String> neighborClasses = new ArrayList<>();
        nearestNeighbors.keySet().forEach(index -> neighborClasses.add(dataSet.getDecisions().get(index)));

        Optional<Map.Entry<String, Long>> mostCommonClass = neighborClasses
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());

        mostCommonClass.ifPresent(entry ->
                System.out.println("Most common class among " + k + " neighbors is " + entry.getKey() + " with " + entry.getValue() + " occurences"));


    }

    private static double calculateDistance(List<Double> point1, List<Double> point2) {
        double sum = 0;
        for (int i = 0; i < point1.size(); i++) {
            double diff = point1.get(i) - point2.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
