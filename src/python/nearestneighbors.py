import numpy as np
from collections import Counter


def calculate(k, data_set, data_point):
    nearest_neighbors = {}

    for i, data_row in enumerate(data_set.get_data_points()):
        distance = calculate_distance(data_point, data_row)

        if len(nearest_neighbors) < k:
            nearest_neighbors[i] = distance
        else:
            farthest_neighbor = max(nearest_neighbors, key=nearest_neighbors.get)
            if distance < nearest_neighbors[farthest_neighbor]:
                nearest_neighbors.pop(farthest_neighbor)
                nearest_neighbors[i] = distance

    neighbor_classes = [data_set.get_decisions()[i] for i in nearest_neighbors.keys()]

    most_common_class = Counter(neighbor_classes).most_common(1)

    if most_common_class:
        most_common_class, occurrences = most_common_class[0]
        print(f"Most common class among {k} neighbors is {most_common_class} with {occurrences} occurrences")


def calculate_distance(point1, point2):
    return np.linalg.norm(np.array(point1) - np.array(point2))
