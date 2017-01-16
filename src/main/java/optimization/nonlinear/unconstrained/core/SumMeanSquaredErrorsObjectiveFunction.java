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

import java.util.ArrayList;
import java.util.List;

public final class SumMeanSquaredErrorsObjectiveFunction implements ObjectiveFunction{
    /** Constant. The stepsize geometric shrink. */
    public static final double STRPSIZE_GEOMETRIC_SHRINK_RHO = 0.4;
    public  static final int NUMBER_OF_POINTS = 9;

    private  List<Double>  deformations = new ArrayList<>();
    private  List<Double>  experimentalStresses = new ArrayList<>();


    public double findValueForArguments(double[] functionArguments) {

        double k = functionArguments[0];
        double n = functionArguments[1];

        double sumOfMeanSquaredErrors=0.0;

        for (int index = 0 ; index<deformations.size();index++){
            double currentCalculatedStress = calculateMaterialStressInPoint(k,n,deformations.get(index));
            sumOfMeanSquaredErrors+=meanSquaredError(experimentalStresses.get(index),currentCalculatedStress);
        }

        return sumOfMeanSquaredErrors;
    }

    public double meanSquaredError(double experimentalStress,double calculatedStress){
        return Math.pow(experimentalStress-calculatedStress,2);
    }

    public double calculateMaterialStressInPoint(double parameterK, double parameterN, double experimentalDeformationValue){
        return parameterK*Math.pow(experimentalDeformationValue,parameterN);
    }

    public void addDeformation(Double deformation){
        deformations.add(deformation);
    }

    public void addExperimentalStress(Double experimentalStress){
        experimentalStresses.add(experimentalStress);
    }

    public  List<Double> getDeformations() {
        return deformations;
    }

    public  List<Double> getExperimentalStresses() {
        return experimentalStresses;
    }

    public  void setDeformations(List<Double> deformations) {
        this.deformations = deformations;
    }

    public  void setExperimentalStresses(List<Double> experimentalStresses) {
        this.experimentalStresses = experimentalStresses;
    }
}
