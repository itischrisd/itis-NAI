class Perceptron:
    def __init__(self, size):
        self.weights = [1.0] * size
        self.threshold = 0.0

    def calculate(self, vector):
        net = self.dot_product(vector)
        return 1 if net >= self.threshold else 0

    def learn(self, vector, d):
        y = self.calculate(vector)
        self.modify_weights(vector, d, y)
        self.modify_threshold(d, y)
        return y

    def modify_weights(self, vector, d, y):
        alpha = 0.0001
        new_weights = [
            weight + (d - y) * alpha * x for weight, x in zip(self.weights, vector)
        ]
        self.weights = self.normalize(new_weights)

    @staticmethod
    def normalize(new_weights):
        length = (sum(weight ** 2 for weight in new_weights)) ** 0.5
        return [weight / length for weight in new_weights]

    def modify_threshold(self, d, y):
        beta = 0.0001
        self.threshold -= (d - y) * beta

    def dot_product(self, vector):
        return sum(x * weight for x, weight in zip(vector, self.weights))

    def __str__(self):
        return f"weights = {self.weights}, threshold = {self.threshold}"
