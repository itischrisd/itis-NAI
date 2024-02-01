package networks;

import java.util.List;

public abstract class Network {

    protected List<String> classes;

    public abstract String decide(List<Double> inputs);

    public abstract double learn(List<Double> inputs, String d, double eta);

}
