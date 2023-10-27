import os
import pandas as pd
import numpy as np
from collections import Counter


def read_csv_file():
    file_name = input("Enter the name of the CSV file to load: ")
    n = input("How many cases to read, 'A' for all: ")

    if not os.path.isfile(file_name):
        raise FileNotFoundError(f"File '{file_name}' does not exist.")

    try:
        if n.lower() == 'a':
            df = pd.read_csv(file_name)
        else:
            n = int(n)
            df = pd.read_csv(file_name, nrows=n)
        return df
    except pd.errors.EmptyDataError:
        raise ValueError(f"File '{file_name}' is empty.")
    except pd.errors.ParserError:
        raise ValueError(f"File '{file_name}' is not a valid CSV file.")


def infer_names():
    if df.columns.equals(df.columns.to_list()):
        print("Unnamed attributes loaded.")
        return 0
    else:
        print("Loaded attribute names:")
        attr_names = list()
        for header in df.columns:
            print(header, end=' ')
            attr_names.append(header)
        print()
        return attr_names


def collect_case():
    case = list()
    for attr in attr_names[:-1]:
        case.append(float(input(f"Enter {attr} value to test: ")))
    return case


def find_neighbors():
    neighbors = dict()
    vectors = df.iloc[:, :].values
    for vec in vectors:
        dist = np.linalg.norm(tested_case - vec[:-1])
        if len(neighbors) < k or dist < max(neighbors.keys()):
            if len(neighbors) == k:
                to_remove = max(neighbors, key=float)
                neighbors.pop(to_remove)
            neighbors[dist] = vec[-1]
    return neighbors


def classify():
    value_counts = Counter(neighbors.values())
    most_common_value, occurrence_count = value_counts.most_common(1)[0]
    print(f"The most common class of neighbots is {most_common_value} with {occurrence_count} occurrences.")


try:
    df = read_csv_file()
    attr_names = infer_names()
    k = int(input("How many nearest neighbors should be calculated? "))
    tested_case = collect_case()
    neighbors = find_neighbors()
    classify()
except (FileNotFoundError, ValueError) as e:
    print(str(e))
