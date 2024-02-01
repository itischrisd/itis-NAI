class Teacher:
    @staticmethod
    def teach(perceptron, data_set, class_value):
        complete = False
        counter = 1
        while not complete:
            complete = True
            for i in range(len(data_set.get_data_points())):
                d = 1 if class_value == data_set.get_decisions()[i] else 0
                y = perceptron.learn(data_set.get_data_points()[i], d)
                complete = complete and d == y
            print("Epochs passed:", counter)
            counter += 1
            if counter > 10000:
                break
