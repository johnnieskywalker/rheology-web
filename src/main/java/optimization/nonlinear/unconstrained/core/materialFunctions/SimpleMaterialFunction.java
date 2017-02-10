package optimization.nonlinear.unconstrained.core.materialFunctions;

import java.util.List;

public class SimpleMaterialFunction implements MaterialFunction {

    public static int NUMBER_OF_PARAMETERS = 2;


    double parameterK;

    double parameterN;

    @Override
    public double calculateMaterialStressInPoint(double experimentalDeformationValue) {
        return calculateMaterialStressInPoint(parameterK, parameterN, experimentalDeformationValue);
    }

    @Override
    public void setNewOptimizedParameterValues(double[] newOptimizedParameterValues) throws IllegalArgumentException {
        if (newOptimizedParameterValues.length < NUMBER_OF_PARAMETERS) {
            throw new IllegalArgumentException("Too few optimized parameters, this material function needs at least 2" +
                    " - k and n which means array size should be " + NUMBER_OF_PARAMETERS + " but was"
                    + newOptimizedParameterValues.length);
        } else {
            parameterK = newOptimizedParameterValues[0];
            parameterN = newOptimizedParameterValues[1];
        }
    }

    @Override
    @Deprecated
    public double calculateMaterialStressInPoint(List<Double> optimizedParameters, double experimentalDeformationValue) {
        return calculateMaterialStressInPoint(optimizedParameters.get(0), optimizedParameters.get(1), experimentalDeformationValue);
    }

    public double calculateMaterialStressInPoint(double parameterK, double parameterN, double experimentalDeformationValue) {
        return parameterK * Math.pow(experimentalDeformationValue, parameterN);
    }

    public double getParameterK() {
        return parameterK;
    }

    public void setParameterK(double parameterK) {
        this.parameterK = parameterK;
    }

    public double getParameterN() {
        return parameterN;
    }

    public void setParameterN(double parameterN) {
        this.parameterN = parameterN;
    }
}
