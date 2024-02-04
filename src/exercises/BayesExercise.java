package exercises;

import java.util.*;

public class BayesExercise {

    private static List<String> attributeNames;
    private static Map<String, List<String>> attributeSpace;
    private static List<List<String>> trainingSet;
    private static List<String> testPoint;

    public static void run() {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        if (random.nextInt() % 2 == 0) {
            createGlassesAttributeSpace();
        } else {
            createTennisAttributeSpace();
        }

        int trainingSetSize = random.nextInt(11) + 10;
        trainingSet = createTrainingSet(trainingSetSize, random);
        testPoint = createTestPoint(random);

        System.out.println("Dla poniższej tabeli decyzyjnej:");
        printTable(attributeNames, trainingSet);

        System.out.println("\nZnajdź prawdopodobieństwo przynależności punktu testowego do każdej z klas.");
        System.out.println("Jeśli jest to konieczne dla jakiegoś atrybutu, to zastosuj wygładzanie dla tego atrybutu.");
        System.out.println("Punkt testowy:");

        printTable(attributeNames.subList(0, attributeNames.size() - 1), List.of(testPoint));
    }

    private static void printTable(List<String> attributeNames, List<List<String>> trainingSet) {
        List<Integer> columnLengths = new ArrayList<>();

        for (String attribute : attributeNames) {
            int maxLength = attribute.length();
            for (String value : attributeSpace.get(attribute)) {
                maxLength = Math.max(maxLength, value.length());
            }
            columnLengths.add(maxLength);
        }

        for (int i = 0; i < attributeNames.size(); i++) {
            System.out.printf("%-" + (columnLengths.get(i) + 1) + "s", attributeNames.get(i));
        }
        System.out.println();

        for (List<String> dataPoint : trainingSet) {
            for (int i = 0; i < dataPoint.size(); i++) {
                System.out.printf("%-" + (columnLengths.get(i) + 1) + "s", dataPoint.get(i));
            }
            System.out.println();
        }
    }

    private static List<String> createTestPoint(Random random) {
        List<String> testPoint = new ArrayList<>();
        List<String> attributes = new ArrayList<>(attributeSpace.keySet());
        for (int i = 0; i < attributes.size() - 1; i++) {
            String attribute = attributes.get(i);
            List<String> attributeValues = attributeSpace.get(attribute);
            testPoint.add(attributeValues.get(random.nextInt(attributeValues.size())));
        }
        if (trainingSet.contains(testPoint)) return createTestPoint(random);
        else return testPoint;
    }

    private static List<List<String>> createTrainingSet(int trainingSetSize, Random random) {
        List<List<String>> trainingSet = new ArrayList<>();
        List<String> attributes = new ArrayList<>(attributeSpace.keySet());
        for (int i = 0; i < trainingSetSize; i++) {
            List<String> dataPoint = new ArrayList<>();
            for (String attribute : attributes) {
                List<String> attributeValues = attributeSpace.get(attribute);
                dataPoint.add(attributeValues.get(random.nextInt(attributeValues.size())));
            }
            trainingSet.add(dataPoint);
        }
        return trainingSet;
    }

    private static void createTennisAttributeSpace() {
        attributeSpace = new LinkedHashMap<>();
        attributeSpace.put("pogoda", List.of("deszczowo", "pochmurno", "słonecznie"));
        attributeSpace.put("temperatura", List.of("chłodno", "ciepło", "normalnie"));
        attributeSpace.put("wilgotność", List.of("wysoka", "normalna"));
        attributeSpace.put("wiatr", List.of("jest", "brak"));
        attributeSpace.put("GRAĆ?", List.of("tak", "nie"));
        attributeNames = new ArrayList<>(attributeSpace.keySet());
    }

    private static void createGlassesAttributeSpace() {
        attributeSpace = new LinkedHashMap<>();
        attributeSpace.put("wiek", List.of("młody", "pre-presbyopic", "presbyopic"));
        attributeSpace.put("presc.", List.of("myope", "hypermetrope"));
        attributeSpace.put("astygmatyzm", List.of("tak", "nie"));
        attributeSpace.put("łzawienie", List.of("normalne", "niskie"));
        attributeSpace.put("OKULARY", List.of("zbędne", "lekkie", "mocne"));
        attributeNames = new ArrayList<>(attributeSpace.keySet());
    }
}
