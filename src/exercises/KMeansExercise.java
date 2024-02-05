package exercises;

import generic.Algebra;

import java.util.*;


public class KMeansExercise {

    private static final Random random = new Random();
    private static final List<List<Double>> vectors = new ArrayList<>();
    private static final List<List<Double>> centroids = new ArrayList<>();
    private static Map<List<Double>, List<List<Double>>> clusters = new HashMap<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int vectorsCount = 4;
        int dimensions = 4;

        for (int i = 0; i < vectorsCount; i++) {
            vectors.add(generateRandomVector(dimensions));
        }

        for (int i = 0; i < 2; i++) {
            centroids.add(generateRandomVector(dimensions));
        }

        System.out.println("Mając dany zbiór danych składający się z 4 wektorów:");
        for (List<Double> vector : vectors) {
            System.out.println(vector);
        }

        System.out.println("oraz początkowe wartości centroidów 2 grup:");
        for (List<Double> centroid : centroids) {
            System.out.println(centroid);
        }

        System.out.println("wykonaj obliczenia dla algorytmu k-średnich (ewentualne remisy w odległościach rozstrzygnij zgodnie z numeracją grup 1 i 2) dla k=2 do momentu zakończenia algorytmu");
        System.out.println("Znak oddzielający część całkowitą od ułamkowej w podawaniu odpowiedzi to przecinek, a nie kropka!");

        calculateKMeans();

        for (int i = 0; i < centroids.size(); i++) {
            List<Double> centroid = centroids.get(i);
            System.out.println("Podaj współrzędne " + (i + 1) + " centroida (współrzędne centroida oddzielone spacją)");
            List<Double> userCentroid = new ArrayList<>();
            for (int j = 0; j < dimensions; j++) {
                userCentroid.add(scanner.nextDouble());
            }
            if (centroid.equals(userCentroid)) {
                System.out.println("Poprawna odpowiedź!");
            } else {
                System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + centroid);
            }
        }

        for (int i = 0; i < clusters.size(); i++) {
            List<Double> centroid = centroids.get(i);
            List<List<Double>> cluster = clusters.get(centroid);
            System.out.println("Podaj wektory przypisane do " + (i + 1) + " centroida (współrzędne wektora oddzielone spacją, wektory w nowej linii)");
            List<List<Double>> userCluster = new ArrayList<>();
            for (int j = 0; j < cluster.size(); j++) {
                List<Double> vector = new ArrayList<>();
                for (int k = 0; k < dimensions; k++) {
                    vector.add(scanner.nextDouble());
                }
                userCluster.add(vector);
            }
            if (new HashSet<>(cluster).containsAll(userCluster) && new HashSet<>(userCluster).containsAll(cluster)) {
                System.out.println("Poprawna odpowiedź!");
            } else {
                System.out.println("Niepoprawna odpowiedź. Prawidłowy wynik to: " + cluster);
            }
        }
    }

    private static void calculateKMeans() {
        clusters = createClusters();
        boolean changed = true;
        while (changed) {
            updateCentroids();
            Map<List<Double>, List<List<Double>>> newClusters;
            newClusters = createClusters();
            if (clusters.equals(newClusters)) {
                changed = false;
            } else {
                clusters = newClusters;
            }
        }
    }

    private static Map<List<Double>, List<List<Double>>> createClusters() {
        Map<List<Double>, List<List<Double>>> newClusters = new HashMap<>();
        for (List<Double> centroid : centroids) {
            newClusters.put(centroid, new ArrayList<>());
        }
        for (List<Double> vector : vectors) {
            List<Double> closestCentroid = findClosestCentroid(vector);
            newClusters.get(closestCentroid).add(vector);
        }
        return newClusters;
    }

    private static void updateCentroids() {
        for (Map.Entry<List<Double>, List<List<Double>>> entry : clusters.entrySet()) {
            List<Double> newCentroid = Algebra.centroid(entry.getValue());
            centroids.set(centroids.indexOf(entry.getKey()), newCentroid);
        }
    }

    private static List<Double> findClosestCentroid(List<Double> vector) {
        List<Double> closestCentroid = centroids.getFirst();
        double minDistance = Double.MAX_VALUE;
        for (List<Double> centroid : centroids) {
            double distance = Algebra.distance(vector, centroid);
            if (distance < minDistance) {
                minDistance = distance;
                closestCentroid = centroid;
            }
        }
        return closestCentroid;
    }

    private static List<Double> generateRandomVector(int size) {
        List<Double> vector = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            vector.add((double) (random.nextInt(5) - 1));
        }
        return vector;
    }
}
