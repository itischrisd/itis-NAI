package generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataPointCollector {

    public static List<Double> collectNumeric(List<String> attributeNames) {
        List<Double> dataPoint = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (String attributeName : attributeNames) {
            String input;
            do {
                System.out.print("Enter a valid value for attribute " + attributeName + ": ");
                input = scanner.nextLine();
            } while (!isValidDouble(input));

            double value = Double.parseDouble(input);
            dataPoint.add(value);
        }

        return dataPoint;
    }

    public static List<String> collectNominal(List<String> attributeNames) {
        List<String> dataPoint = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (String attributeName : attributeNames) {
            System.out.print("Enter a valid value for attribute " + attributeName + ": ");
            String value = scanner.nextLine();
            dataPoint.add(value);
        }

        return dataPoint;
    }

    private static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}