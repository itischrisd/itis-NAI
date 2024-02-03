package knn;

import generic.Algebra;
import generic.NumericDataSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearestNeighbors {

    public static String calculate(int k, NumericDataSet numericDataSet, List<Double> dataPoint) {
        Map<Integer, Double> indexAndDistanceOfNN = new HashMap<>();

        for (int i = 0; i < numericDataSet.getDataPoints().size(); i++) {
            double distance = Algebra.distance(dataPoint, numericDataSet.getDataPoints().get(i));

            if (indexAndDistanceOfNN.size() < k) {
                indexAndDistanceOfNN.put(i, distance);
            } else {
                int farthestNeighborIndex = Collections.max(indexAndDistanceOfNN.entrySet(), Map.Entry.comparingByValue()).getKey();
                if (distance < indexAndDistanceOfNN.get(farthestNeighborIndex)) {
                    indexAndDistanceOfNN.remove(farthestNeighborIndex);
                    indexAndDistanceOfNN.put(i, distance);
                }
            }
        }

        List<String> decisions = numericDataSet.getDecisions().stream().distinct().toList();
        Map<String, Integer> decisionCount = new HashMap<>();
        decisions.forEach(decision -> decisionCount.put(decision, 0));

        for (Integer index : indexAndDistanceOfNN.keySet()) {
            decisionCount.put(numericDataSet.getDecisions().get(index), decisionCount.get(numericDataSet.getDecisions().get(index)) + 1);
        }

        int maxCount = Collections.max(decisionCount.values());

        String finalDecision = null;
        for (String decision : decisionCount.keySet()) {
            if (decisionCount.get(decision) == maxCount) {
                finalDecision = decision;
            }
        }
        return finalDecision;
    }
}
