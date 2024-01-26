package knn;

import generic.Algebra;
import generic.DataSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearestNeighbors {

    public static String calculate(int k, DataSet dataSet, List<Double> dataPoint) {
        Map<Integer, Double> indexAndDistanceOfNN = new HashMap<>();

        for (int i = 0; i < dataSet.getDataPoints().size(); i++) {
            double distance = Algebra.distance(dataPoint, dataSet.getDataPoints().get(i));

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

        List<String> decisions = dataSet.getDecisions().stream().distinct().toList();
        Map<String, Integer> decisionCount = new HashMap<>();
        decisions.forEach(decision -> decisionCount.put(decision, 0));

        for (Integer index : indexAndDistanceOfNN.keySet()) {
            decisionCount.put(dataSet.getDecisions().get(index), decisionCount.get(dataSet.getDecisions().get(index)) + 1);
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
