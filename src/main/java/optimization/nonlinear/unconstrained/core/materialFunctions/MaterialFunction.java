package optimization.nonlinear.unconstrained.core.materialFunctions;

import java.util.List;

public interface MaterialFunction {

    public double calculateMaterialStressInPoint(double experimentalDeformationValue);

    double calculateMaterialStressInPoint(List<Double> optimizedParameters, double
            experimentalDeformationValue);

    void setNewOptimizedParameterValues(double [] newOptimizedParameterValues) throws IllegalArgumentException;
}
