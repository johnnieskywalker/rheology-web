package optimization.nonlinear.unconstrained.core.materialFunctions;

public interface MaterialFunction {

    public double calculateMaterialStressInPoint(double parameterK, double parameterN, double
            experimentalDeformationValue);
}
