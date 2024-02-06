package optimisation;

import java.util.*;

public class TravellingSalesman {

    private final int[][] distances;

    public TravellingSalesman(int numberOfCities) {
        this.distances = new int[numberOfCities][numberOfCities];
        Random random = new Random();
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = i + 1; j < numberOfCities; j++) {
                int distance = random.nextInt(100) + 1;
                distances[i][j] = distance;
                distances[j][i] = distance;
            }
        }
    }

    public List<Integer> bruteForce() {
        List<Integer> bestRoute = new ArrayList<>();
        int bestDistance = Integer.MAX_VALUE;
        List<List<Integer>> allRoutes = generateAllRoutes();
        for (List<Integer> route : allRoutes) {
            int distance = evaluateRoute(route);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestRoute = route;
            }
        }
        return bestRoute;
    }

    public List<Integer> greedy() {
        List<Integer> route = new ArrayList<>();
        Random random = new Random();
        int currentCity = random.nextInt(distances.length);
        route.add(currentCity);
        while (route.size() < distances.length) {
            int nextCity = findClosestCity(currentCity, route);
            route.add(nextCity);
            currentCity = nextCity;
        }
        return route;
    }

    public List<Integer> hillClimbing() {
        List<Integer> currentRoute = generateRandomRoute();
        int currentDistance = evaluateRoute(currentRoute);
        List<List<Integer>> neighbours;
        boolean improved = true;

        while (improved) {
            improved = false;
            neighbours = generateNeighbours(currentRoute);
            for (List<Integer> neighbour : neighbours) {
                int neighbourDistance = evaluateRoute(neighbour);
                if (neighbourDistance < currentDistance) {
                    currentRoute = neighbour;
                    currentDistance = neighbourDistance;
                    improved = true;
                    break;
                }
            }
        }

        return currentRoute;
    }

    private List<List<Integer>> generateNeighbours(List<Integer> currentRoute) {
        List<List<Integer>> neighbours = new ArrayList<>();
        for (int i = 0; i < currentRoute.size(); i++) {
            for (int j = i + 1; j < currentRoute.size(); j++) {
                List<Integer> neighbour = reverseSublist(currentRoute, i, j);
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    private List<Integer> reverseSublist(List<Integer> currentRoute, int i, int j) {
        List<Integer> neighbour = new ArrayList<>(currentRoute);
        if (i < j) {
            Collections.reverse(neighbour.subList(i, j + 1));
        } else {
            Collections.reverse(neighbour);
            Collections.reverse(neighbour.subList(i, j + 1));
        }
        return neighbour;
    }

    private List<Integer> generateRandomRoute() {
        List<Integer> route = new ArrayList<>();
        for (int i = 0; i < distances.length; i++) {
            route.add(i);
        }
        Collections.shuffle(route);
        return route;
    }

    private int findClosestCity(int currentCity, List<Integer> route) {
        List<Integer> unvisitedCities = new ArrayList<>();
        for (int i = 0; i < distances.length; i++) {
            if (!route.contains(i)) {
                unvisitedCities.add(i);
            }
        }
        int closestCity = unvisitedCities.getFirst();
        int closestDistance = distances[currentCity][closestCity];
        for (int city : unvisitedCities) {
            if (distances[currentCity][city] < closestDistance) {
                closestCity = city;
                closestDistance = distances[currentCity][city];
            }
        }
        return closestCity;
    }

    private List<List<Integer>> generateAllRoutes() {
        List<List<Integer>> allRoutes = new ArrayList<>(distances.length);
        for (int i = 0; i < distances.length; i++) {
            List<Integer> route = new ArrayList<>();
            route.add(i);
            allRoutes.addAll(generateRoutes(route));
        }
        return allRoutes;
    }

    private Collection<? extends List<Integer>> generateRoutes(List<Integer> route) {
        List<List<Integer>> routes = new ArrayList<>();
        if (route.size() == distances.length) {
            routes.add(route);
        } else {
            for (int i = 0; i < distances.length; i++) {
                if (!route.contains(i)) {
                    List<Integer> newRoute = new ArrayList<>(route);
                    newRoute.add(i);
                    routes.addAll(generateRoutes(newRoute));
                }
            }
        }
        return routes;
    }

    private int evaluateRoute(List<Integer> route) {
        int distance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            distance += distances[route.get(i)][route.get(i + 1)];
        }
        distance += distances[route.getLast()][route.getFirst()];
        return distance;
    }

    public void printRoute(List<Integer> route) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < route.size() - 1; i++) {
            sb.append(route.get(i)).append(" --(").append(distances[route.get(i)][route.get(i + 1)]).append(")--> ");
        }
        sb.append(route.getLast());
        System.out.println(sb);
        System.out.println("Długość trasy: " + evaluateRoute(route));
    }
}
