package decision;

import generic.NominalDataSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecisionTree {

    private final List<String> attributeNames;
    private final DecisionTreeNode root;

    public DecisionTree(NominalDataSet nominalDataSet) {
        this.attributeNames = nominalDataSet.getAttributeNames();
        this.root = buildTree(nominalDataSet.getAttributeNames(), nominalDataSet.getDataPoints(), nominalDataSet.getDecisions());
    }

    private DecisionTreeNode buildTree(List<String> attributeNames, List<List<String>> dataPoints, List<String> decisions) {
        if (decisions.stream().distinct().count() == 1) {
            DecisionTreeNode leaf = new DecisionTreeNode(null);
            leaf.setClassLabel(decisions.getFirst());
            return leaf;
        }

        if (attributeNames.isEmpty()) {
            DecisionTreeNode leaf = new DecisionTreeNode(null);
            leaf.setClassLabel(getMostCommonClass(decisions));
            return leaf;
        }

        if (decisions.size() <= 2) {
            DecisionTreeNode leaf = new DecisionTreeNode(null);
            leaf.setClassLabel(getMostCommonClass(decisions));
            return leaf;
        }

        Map<String, Double> attributeEntropies = new HashMap<>();

        for (int i = 0; i < attributeNames.size(); i++) {
            int finalI = i;
            List<String> attributeValues = dataPoints.stream().map(dataPoint -> dataPoint.get(finalI)).distinct().toList();
            Map<String, List<String>> valuesWithDecisions = new HashMap<>();

            for (String value : attributeValues) {
                List<String> decisionsForValue = dataPoints.stream().filter(dataPoint -> dataPoint.get(finalI).equals(value)).map(dataPoint -> decisions.get(dataPoints.indexOf(dataPoint))).toList();
                valuesWithDecisions.put(value, decisionsForValue);
            }

            double averageEntropy = calculateAverageEntropy(valuesWithDecisions);
            attributeEntropies.put(attributeNames.get(i), averageEntropy);
        }

        String bestAttribute = attributeEntropies.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
        DecisionTreeNode currentBestAttribute = new DecisionTreeNode(bestAttribute);
        List<String> bestAttributeValues = dataPoints.stream().map(dataPoint -> dataPoint.get(attributeNames.indexOf(bestAttribute))).distinct().toList();

        for (String value : bestAttributeValues) {
            List<String> reducedAttributeNames = attributeNames.stream().filter(attributeName -> !attributeName.equals(bestAttribute)).toList();
            List<List<String>> reducedDataPoints = dataPoints.stream().filter(dataPoint -> dataPoint.get(attributeNames.indexOf(bestAttribute)).equals(value)).toList();
            List<String> reducedDecisions = reducedDataPoints.stream().map(dataPoint -> decisions.get(dataPoints.indexOf(dataPoint))).toList();
            reducedDataPoints = reducedDataPoints.stream().map(dataPoint -> dataPoint.stream().filter(v -> !v.equals(value)).toList()).toList();
            currentBestAttribute.addChild(value, buildTree(reducedAttributeNames, reducedDataPoints, reducedDecisions));
        }

        return currentBestAttribute;
    }

    private double calculateAverageEntropy(Map<String, List<String>> attribute) {
        double averageEntropy = 0;

        for (String key : attribute.keySet()) {
            double entropy = 0;
            int totalCount = attribute.get(key).size();
            List<String> possibleValues = attribute.get(key).stream().distinct().toList();

            for (String value : possibleValues) {
                int valueCount = attribute.get(key).stream().filter(v -> v.equals(value)).toList().size();
                double probability = (double) valueCount / totalCount;
                entropy += -probability * Math.log(probability);
            }

            averageEntropy += entropy;
        }

        averageEntropy /= attribute.size();
        return averageEntropy;
    }

    private static String getMostCommonClass(List<String> classes) {
        HashMap<String, Integer> map = new HashMap<>();

        for (String string : classes) {
            if (map.containsKey(string)) {
                map.put(string, map.get(string) + 1);
            } else {
                map.put(string, 1);
            }
        }

        String mostCommonClass = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostCommonClass = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return mostCommonClass;
    }

    public void printTree() {
        printTreeNode(root, "", true);
    }

    private void printTreeNode(DecisionTreeNode node, String indent, boolean last) {
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("└── ");
                indent += "    ";
            } else {
                System.out.print("├── ");
                indent += "|   ";
            }
            if (node.getClassLabel() != null) {
                System.out.println(node.getClassLabel());
            } else {
                System.out.println(node.getAttribute());
            }

            Map<String, DecisionTreeNode> children = node.getChildren();
            if (children != null) {
                int i = 0;
                for (Map.Entry<String, DecisionTreeNode> entry : children.entrySet()) {
                    String childName = entry.getKey();
                    DecisionTreeNode childNode = entry.getValue();
                    System.out.println(indent + " (" + childName + ")");
                    printTreeNode(childNode, indent, ++i == children.size());
                }
            }
        }
    }

    public String classify(List<String> dataPoint) {
        return classifyNode(dataPoint, root);
    }

    private String classifyNode(List<String> dataPoint, DecisionTreeNode root) {
        if (root.getClassLabel() != null) {
            return root.getClassLabel();
        }

        System.out.println("Classifying with attribute: " + root.getAttribute());
        String attribute = root.getAttribute();
        String value = dataPoint.get(attributeNames.indexOf(attribute));
        DecisionTreeNode child = root.getChildren().get(value);
        return classifyNode(dataPoint, child);
    }
}
