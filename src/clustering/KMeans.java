package clustering;

import generic.Algebra;
import generic.NumericDataSet;

import java.util.*;

public class KMeans {

    private final List<List<Double>> dataPoints;
    private final Random random;
    private final List<List<Double>> centroids;
    private Map<List<Double>, List<List<Double>>> clusters;

    public KMeans(NumericDataSet dataSet) {
        this.dataPoints = dataSet.getAllDataPoints();
        this.random = new Random();
        this.centroids = new ArrayList<>();
    }

    public void cluster(int k) {
        randomlyCreateCentroids(k);
        boolean centroidsChanged = true;
        while (centroidsChanged) {
            calculateNewClusterAssignments();
            Map<List<Double>, List<List<Double>>> newClusters = createClustersWithNewCentroids();
            if (clusters.equals(newClusters)) {
                centroidsChanged = false;
            } else {
                clusters = newClusters;
            }
        }
    }

    private void calculateNewClusterAssignments() {
        clusters = new HashMap<>();
        for (List<Double> centroid : centroids) {
            clusters.put(centroid, new ArrayList<>());
        }
        for (List<Double> dataPoint : dataPoints) {
            List<Double> closestCentroid = findClosestCentroid(dataPoint);
            clusters.get(closestCentroid).add(dataPoint);
        }
    }

    private List<Double> findClosestCentroid(List<Double> vector) {
        List<Double> closestCentroid = new ArrayList<>();
        double minDistance = Double.MAX_VALUE;
        for (List<Double> centroid : clusters.keySet()) {
            double distance = Algebra.distance(vector, centroid);
            if (distance < minDistance) {
                minDistance = distance;
                closestCentroid = centroid;
            }
        }
        return closestCentroid;
    }

    private void randomlyCreateCentroids(int k) {
        clusters = new HashMap<>();
        for (int i = 0; i < k; i++) {
            int randomIndex = random.nextInt(dataPoints.size());
            centroids.add(dataPoints.get(randomIndex));
            clusters.put(centroids.get(i), new ArrayList<>());
        }

        for (List<Double> dataPoint : dataPoints) {
            List<Double> randomCentroid = centroids.get(random.nextInt(centroids.size()));
            clusters.get(randomCentroid).add(dataPoint);
        }

        clusters = createClustersWithNewCentroids();
    }

    private Map<List<Double>, List<List<Double>>> createClustersWithNewCentroids() {
        Map<List<Double>, List<List<Double>>> newClusters = new HashMap<>();
        for (Map.Entry<List<Double>, List<List<Double>>> entry : clusters.entrySet()) {
            List<Double> newCentroid = Algebra.centroid(entry.getValue());
            centroids.set(centroids.indexOf(entry.getKey()), newCentroid);
            newClusters.put(newCentroid, entry.getValue());
        }
        return newClusters;
    }

    public void printClusters() {
        for (List<Double> centroid : clusters.keySet()) {
            System.out.println("Centroid: " + centroid);
            for (List<Double> dataPoint : clusters.get(centroid)) {
                System.out.println(dataPoint);
            }
            System.out.println();
        }
    }
}
