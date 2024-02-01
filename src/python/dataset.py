class DataSet:
    def __init__(self, file_path):
        self.attribute_names = []
        self.data_points = []
        self.decisions = []

        with open(file_path, 'r') as file:
            lines = file.readlines()

            items = lines[0].strip().split(',')
            if any(any(char.isalpha() for char in item) for item in items[:-1]):
                self.attribute_names.extend(items)
                self.attribute_names.pop()
            else:
                for i in range(len(items) - 1):
                    self.attribute_names.append(str(i + 1))
                self.data_points.append([float(item) for item in items[:-1]])
                self.decisions.append(items[-1].replace('"', ''))

            for line in lines[1:]:
                items = line.strip().split(',')
                self.data_points.append([float(item) for item in items[:-1]])
                self.decisions.append(items[-1].replace('"', ''))

    @staticmethod
    def parse_csv(file_path):
        return DataSet(file_path)

    def get_attribute_names(self):
        return self.attribute_names

    def get_data_points(self):
        return self.data_points

    def get_decisions(self):
        return self.decisions

    def print(self):
        print('\t'.join(self.attribute_names) + '\tclass')
        for i in range(len(self.data_points)):
            print('\t'.join(map(str, self.data_points[i])) + '\t' + self.decisions[i])
        print('Total data points:', len(self.data_points))
