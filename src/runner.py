from generic.dataset import DataSet
from generic.fileselector import FileSelector
from knn.nearestneighbors import calculate
from perceptron.perceptron import Perceptron
from perceptron.teacher import Teacher

file_path = FileSelector.get_file_path("../data/iris.csv")
data_set = DataSet.parse_csv(file_path)
test_data_point = [4.8, 3.0, 1.4, 0.1]

calculate(7, data_set, test_data_point)

perceptron = Perceptron(len(data_set.attribute_names))
Teacher.teach(perceptron, data_set, "Setosa")
result = perceptron.calculate(test_data_point)
print(f"Perceptron prediction: {result}")
