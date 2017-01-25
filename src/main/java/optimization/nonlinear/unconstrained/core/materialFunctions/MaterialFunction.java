package optimization.nonlinear.unconstrained.core.materialFunctions;

import java.util.List;

public interface MaterialFunction {

    double calculateMaterialStressInPoint(List<Double> optimizedParameters, double
            experimentalDeformationValue);
}
