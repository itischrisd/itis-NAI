def collect(attribute_names):
    data_point = []
    for attribute_name in attribute_names:
        while True:
            input_value = input(f"Enter a valid value for attribute {attribute_name}: ")
            try:
                value = float(input_value)
                data_point.append(value)
                break  # Exit the loop if a valid value is entered
            except ValueError:
                print("Invalid input. Please enter a valid float value.")

    return data_point
