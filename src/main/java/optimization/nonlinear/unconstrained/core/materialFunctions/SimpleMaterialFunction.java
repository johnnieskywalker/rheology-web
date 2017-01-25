package optimization.nonlinear.unconstrained.core.materialFunctions;

import java.util.List;

public class SimpleMaterialFunction implements MaterialFunction{

    @Override
    public double calculateMaterialStressInPoint(List<Double> optimizedParameters, double experimentalDeformationValue) {
        return calculateMaterialStressInPoint(optimizedParameters.get(0), optimizedParameters.get(1),experimentalDeformationValue );
    }

    public double calculateMaterialStressInPoint(double parameterK, double parameterN, double experimentalDeformationValue){
        return parameterK*Math.pow(experimentalDeformationValue,parameterN);
    }
}
