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

public final class SumMeanSquaredErrors implements ObjectiveFunction{
    /** Constant. The stepsize geometric shrink. */
    public static final double RHO_BEGIN = 0.4;
    public  static final int NUMBER_OF_POINTS = 9;

    private static List<Double>  deformations = new ArrayList<>();
    private static List<Double>  experimentalStresses = new ArrayList<>();


    public double findValueForArguments(final double[] x) {

        double k = x[0];
        double n = x[1];

        double sumOfMeanSquaredErrors=0.0;

        for (int index = 0 ; index<deformations.size();index++){
            double currentCalculatedStress = calculateMaterialStressInPoint(k,n,deformations.get(index));
            sumOfMeanSquaredErrors+=meanSquaredError(experimentalStresses.get(index),currentCalculatedStress);
        }

        return sumOfMeanSquaredErrors;
    }

    public static double meanSquaredError(double experimentalDeformation,double calculatedDeformation){
        return Math.pow(experimentalDeformation-calculatedDeformation,2);
    }

    public static double calculateMaterialStressInPoint(double parameterK, double parameterN, double deformationValueInIteration){
        return parameterK*Math.pow(deformationValueInIteration,parameterN);
    }

    public static List<Double> getDeformations() {
        return deformations;
    }

    public static List<Double> getExperimentalStresses() {
        return experimentalStresses;
    }

    public static void setDeformations(List<Double> deformations) {
        SumMeanSquaredErrors.deformations = deformations;
    }

    public static void setExperimentalStresses(List<Double> experimentalStresses) {
        SumMeanSquaredErrors.experimentalStresses = experimentalStresses;
    }
}
