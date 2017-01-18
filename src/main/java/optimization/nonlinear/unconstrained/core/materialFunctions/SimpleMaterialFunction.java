package optimization.nonlinear.unconstrained.core.materialFunctions;

public class SimpleMaterialFunction implements MaterialFunction{

    @Override
    public double calculateMaterialStressInPoint(double parameterK, double parameterN, double experimentalDeformationValue){
        return parameterK*Math.pow(experimentalDeformationValue,parameterN);
    }
}
