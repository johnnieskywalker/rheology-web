package optimization.nonlinear.unconstrained.core;

public interface ObjectiveFunction {

    public double findValueForArguments(double... parameters);

    public double meanSquaredError(double experimentalDeformation,double calculatedDeformation);

    public double calculateMaterialStressInPoint(double parameterK, double parameterN, double
            deformationValueInIteration);

}
