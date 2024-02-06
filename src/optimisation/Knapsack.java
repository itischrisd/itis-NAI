package optimisation;

import generic.Algebra;

import java.util.*;

public class Knapsack {

    private final List<String> items;
    private final List<Integer> weights;
    private final List<Integer> values;
    private final int capacity;

    public Knapsack(int numberOfItems) {
        this.items = generateRandomItems(numberOfItems);
        this.weights = generateRandomNumbers(numberOfItems);
        this.values = generateRandomNumbers(numberOfItems);
        this.capacity = generateRandomCapacity();
    }

    public List<Integer> hillClimbing() {
        List<Integer> currentCharacteristicVector = generateRandomCharacteristicVector();
        int currentValue = evaluateCharacteristicVector(currentCharacteristicVector);
        List<List<Integer>> neighbours;
        boolean improved = true;

        while (improved) {
            improved = false;
            neighbours = generateNeighbours(currentCharacteristicVector);
            for (List<Integer> neighbour : neighbours) {
                int neighbourValue = evaluateCharacteristicVector(neighbour);
                if (neighbourValue > currentValue) {
                    currentCharacteristicVector = neighbour;
                    currentValue = neighbourValue;
                    improved = true;
                }
            }
        }
        return currentCharacteristicVector;
    }

    private List<List<Integer>> generateNeighbours(List<Integer> currentCharacteristicVector) {
        List<List<Integer>> neighbours = new ArrayList<>();
        for (int i = 0; i < currentCharacteristicVector.size(); i++) {
            List<Integer> neighbour = new ArrayList<>(currentCharacteristicVector);
            neighbour.set(i, neighbour.get(i) == 0 ? 1 : 0);
            neighbours.add(neighbour);
        }
        return neighbours;
    }

    private List<Integer> generateRandomCharacteristicVector() {
        Random random = new Random();
        List<Integer> characteristicVector = new ArrayList<>(items.size());
        for (int i = 0; i < items.size(); i++) {
            characteristicVector.add(random.nextInt(2));
        }
        return characteristicVector;
    }

    private List<String> generateRandomItems(int numberOfItems) {
        List<String> items = new ArrayList<>(numberOfItems);
        for (int i = 0; i < numberOfItems; i++) {
            items.add(letterFromInt(i));
        }
        return items;
    }

    private String letterFromInt(int i) {
        if (i < 26) {
            return String.valueOf((char) (i + 97));
        } else {
            return String.valueOf((char) (i + 65));
        }
    }

    private List<Integer> generateRandomNumbers(int numberOfItems) {
        Random random = new Random();
        List<Integer> weights = new ArrayList<>(numberOfItems);
        for (int i = 0; i < numberOfItems; i++) {
            weights.add(random.nextInt(10) + 1);
        }
        return weights;
    }

    private int generateRandomCapacity() {
        Random random = new Random();
        int totalWeight = weights.stream().mapToInt(Integer::intValue).sum();
        int minimumCapacity = totalWeight / 2;
        return random.nextInt(totalWeight - minimumCapacity) + minimumCapacity;
    }

    public List<Integer> bruteForce() {
        List<Integer> characteristicVector = new ArrayList<>(Collections.nCopies(items.size(), 0));
        List<Integer> bestCharacteristicVector = new ArrayList<>(Collections.nCopies(items.size(), 0));
        int bestValue = 0;
        int subSetCount = (int) Math.pow(2, items.size());
        for (int i = 0; i < subSetCount; i++) {
            int value = evaluateCharacteristicVector(characteristicVector);
            if (value > bestValue) {
                bestValue = value;
                bestCharacteristicVector = new ArrayList<>(characteristicVector);
            }
            characteristicVector = Algebra.incrementBinaryVector(characteristicVector);
        }
        return bestCharacteristicVector;
    }

    private int evaluateCharacteristicVector(List<Integer> characteristicVector) {
        int totalWeight = 0;
        int totalValue = 0;
        for (int i = 0; i < items.size(); i++) {
            if (characteristicVector.get(i) == 1) {
                totalWeight += weights.get(i);
                totalValue += values.get(i);
            }
        }
        if (totalWeight <= capacity) {
            return totalValue;
        } else {
            return 0;
        }
    }

    public void printSolution(List<Integer> characteristicVector) {
        int totalWeight = 0;
        int totalValue = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (characteristicVector.get(i) == 1) {
                sb.append(items.get(i)).append(" ");
                sb.append("(w:").append(weights.get(i)).append(", v:");
                sb.append(values.get(i)).append("), ");
                totalWeight += weights.get(i);
                totalValue += values.get(i);
            }
        }
        sb.delete(sb.length() - 2, sb.length());
        System.out.println(sb);
        System.out.println("Pojemność plecaka: " + capacity);
        System.out.println("Suma wag: " + totalWeight);
        System.out.println("Suma wartości: " + totalValue);
    }

    public List<Integer> greedy() {
        List<Integer> characteristicVector = new ArrayList<>(Collections.nCopies(items.size(), 0));
        List<Integer> sortedIndices = new ArrayList<>(items.size());
        for (int i = 0; i < items.size(); i++) {
            sortedIndices.add(i);
        }
        sortedIndices.sort(Comparator.comparingDouble(i -> (double) values.get((Integer) i) / weights.get((Integer) i)).reversed());
        int remainingCapacity = capacity;
        for (int i = 0; i < items.size(); i++) {
            int index = sortedIndices.get(i);
            if (weights.get(index) <= remainingCapacity) {
                characteristicVector.set(index, 1);
                remainingCapacity -= weights.get(index);
            }
        }
        return characteristicVector;
    }
}
