package optimization.nonlinear.unconstrained.core;

import optimization.nonlinear.unconstrained.core.materialFunctions.MaterialFunction;

public interface ObjectiveFunction {

    public double findValueForArguments(double... parameters);

    public double rootMeanSquaredError(double experimentalDeformation, double calculatedDeformation);

    public double calculateMaterialStressInPoint(double experimentalDeformationValue);

    public MaterialFunction getMaterialFunction();

    @Deprecated
    public double calculateMaterialStressInPoint(double parameterK, double parameterN, double
            deformationValueInIteration);

}
