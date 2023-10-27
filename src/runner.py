from generic.dataset import DataSet
from generic.fileselector import FileSelector

file_path = FileSelector.get_file_path("../data/iris.csv")
data = DataSet.parse_csv(file_path)