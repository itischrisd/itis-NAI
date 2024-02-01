import os


class FileSelector:
    @staticmethod
    def get_file_path(file_path):
        if file_path:
            return file_path

        while True:
            input_path = input("Enter a valid data set file path: ")
            if os.path.exists(input_path):
                return input_path
