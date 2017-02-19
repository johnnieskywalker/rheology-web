/*
 * nlp-unconstrained-core/hooke-jeeves/java/src/main/java/
 * optimization/nonlinear/unconstrained/core/Rosenbrock.java
 * ============================================================================
 * Nonlinear Optimization Algorithms Multilang. Version 0.1
 * ============================================================================
 * Nonlinear programming algorithms as the (un-)constrained minimization
 * problems with the focus on their numerical expression using various
 * programming languages.
 *
 * This is the Hooke and Jeeves nonlinear unconstrained minimization algorithm.
 * ============================================================================
 */

package optimization.nonlinear.unconstrained.core;

import optimization.nonlinear.unconstrained.core.materialFunctions.MaterialFunction;

import java.util.ArrayList;
import java.util.List;

public final class SumRootMeanSquaredErrorsObjectiveFunction implements ObjectiveFunction {
    public static final double STRPSIZE_GEOMETRIC_SHRINK_RHO = 0.4;
    public static final int NUMBER_OF_POINTS = 9;

    private List<Double> deformations = new ArrayList<>();
    private List<Double> experimentalStresses = new ArrayList<>();

    private MaterialFunction materialFunction;// = new SimpleMaterialFunction();

    public double findValueForArguments(double[] functionArguments) {

//        double k = functionArguments[0];
//        double n = functionArguments[1];

        double sumOfMeanSquaredErrors = 0.0;

        for (int index = 0; index < deformations.size(); index++) {
//            double currentCalculatedStress = calculateMaterialStressInPoint(k, n, deformations.get(index));
            double currentCalculatedStress = calculateMaterialStressInPoint(deformations.get(index));
            sumOfMeanSquaredErrors += rootMeanSquaredError(experimentalStresses.get(index), currentCalculatedStress);
        }

        return sumOfMeanSquaredErrors;
    }

    public double rootMeanSquaredError(double experimentalStress, double calculatedStress) {
        return Math.sqrt(Math.pow(experimentalStress - calculatedStress, 2));
    }

    public double calculateMaterialStressInPoint(double experimentalDeformationValue) {
        return materialFunction.calculateMaterialStressInPoint(experimentalDeformationValue);
    }

    @Deprecated
    //Used only by converter
    public double calculateMaterialStressInPoint(double parameterK, double parameterN, double experimentalDeformationValue) {
        ArrayList<Double> parameters = new ArrayList<>();
        parameters.add(parameterK);
        parameters.add(parameterN);
        return materialFunction.calculateMaterialStressInPoint(parameters,experimentalDeformationValue);
    }

    public void addDeformation(Double deformation) {
        deformations.add(deformation);
    }

    public void addExperimentalStress(Double experimentalStress) {
        experimentalStresses.add(experimentalStress);
    }

    public List<Double> getDeformations() {
        return deformations;
    }

    public List<Double> getExperimentalStresses() {
        return experimentalStresses;
    }

    public void setDeformations(List<Double> deformations) {
        this.deformations = deformations;
    }

    public void setExperimentalStresses(List<Double> experimentalStresses) {
        this.experimentalStresses = experimentalStresses;
    }

    @Override
    public MaterialFunction getMaterialFunction() {
        return materialFunction;
    }

    public void setMaterialFunction(MaterialFunction materialFunction) {
        this.materialFunction = materialFunction;
    }
}
