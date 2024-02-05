package bayes;

import generic.NominalDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayes {

    private final List<List<String>> dataPoints;
    private final List<String> decisions;

    public NaiveBayes(NominalDataSet dataSet) {
        this.dataPoints = dataSet.getDataPoints();
        this.decisions = dataSet.getDecisions();
    }

    public String decide(List<String> testPoint) {
        Map<String, Double> probabilities = calculateProbabilities(testPoint);
        String decision = "";
        double maxProbability = 0.0;

        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            if (entry.getValue() > maxProbability) {
                maxProbability = entry.getValue();
                decision = entry.getKey();
            }
        }

        return decision;
    }

    public Map<String, Double> calculateProbabilities(List<String> testPoint) {
        List<String> classes = this.decisions.stream().distinct().toList();
        List<Double> probabilities = classes.stream().map(c -> calculateDecisionProbability(testPoint, c)).toList();
        Map<String, Double> classProbabilities = new HashMap<>();

        for (int i = 0; i < classes.size(); i++) {
            classProbabilities.put(classes.get(i), probabilities.get(i));
        }

        return classProbabilities;
    }

    private double calculateDecisionProbability(List<String> testPoint, String aClass) {
        double classProbability = calculateClassProbability(aClass);
        double attributeProbability = 1.0;

        for (int i = 0; i < testPoint.size(); i++) {
            attributeProbability *= calculateAttributeProbability(i, testPoint.get(i), aClass);
        }

        return classProbability * attributeProbability;
    }

    private double calculateAttributeProbability(int index, String attributeValue, String aClass) {
        List<String> attributeValues = new ArrayList<>();

        for (int i = 0; i < this.dataPoints.size(); i++) {
            if (this.decisions.get(i).equals(aClass)) {
                attributeValues.add(this.dataPoints.get(i).get(index));
            }
        }

        double matchingAttributeCount = (double) attributeValues.stream().filter(a -> a.equals(attributeValue)).count();
        double allAttributeCount = attributeValues.size();

        if (matchingAttributeCount == 0) {
            matchingAttributeCount++;
            allAttributeCount += dataPoints.stream().map(e -> e.get(index)).distinct().count();
        }

        return matchingAttributeCount / allAttributeCount;
    }

    private double calculateClassProbability(String aClass) {
        return (double) this.decisions.stream().filter(d -> d.equals(aClass)).count() / this.decisions.size();
    }
}
