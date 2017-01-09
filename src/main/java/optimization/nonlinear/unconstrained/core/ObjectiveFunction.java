package optimization.nonlinear.unconstrained.core;

public interface ObjectiveFunction {

    public double findValueForArguments(double... parameters);

}
