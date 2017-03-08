package optimization.nonlinear.unconstrained.core.materialFunctions;

import settings.MaterialFunctionType;

import java.util.List;

public interface MaterialFunction {

    double calculateMaterialStressInPoint(double experimentalDeformationValue);

    double calculateMaterialStressInPoint(List<Double> optimizedParameters, double
            experimentalDeformationValue);

    void setNewOptimizedParameterValues(double[] newOptimizedParameterValues) throws IllegalArgumentException;

    MaterialFunctionType getType();
}
