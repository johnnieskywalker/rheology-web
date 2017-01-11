package optimization.nonlinear.unconstrained.examples;

import optimization.nonlinear.unconstrained.core.SumMeanSquaredErrorsObjectiveFunction;
import optimization.nonlinear.unconstrained.core.Hooke;

public class ApproximationSimpleUse {


    public static void main(final String[] args) {

        SumMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction = new SumMeanSquaredErrorsObjectiveFunction();


        Hooke hooke = new Hooke();

        mockExperimentalData(sumMeanSquaredErrorsObjectiveFunction);

        int nVars = 2;
        int iterMax = Hooke.MAXIMUM_NUMBER_OF_ITERATIONS;
        double rho = sumMeanSquaredErrorsObjectiveFunction.RHO_BEGIN;
        double epsilon = Hooke.ENDING_VALUE_OF_STEPSIZE;
        double[] endPt = new double[Hooke.MAXIMUM_NUMBER_OF_VARIABLES];
        double[] startPt = new double[Hooke.MAXIMUM_NUMBER_OF_VARIABLES];
        startPt[Hooke.INDEX_ZERO] = 0.1;
        startPt[Hooke.INDEX_ONE] = 0.1;

        hooke.findMinimum(
                nVars, startPt, endPt, rho, epsilon, iterMax, sumMeanSquaredErrorsObjectiveFunction);
        int numberOfIterations = hooke.getNumberOfIterations();

        printResults(nVars, numberOfIterations, endPt);
        System.out.println("\n\nin hooke object:");
        printResults(nVars, numberOfIterations, hooke.getResultPointCoordinates());
    }

    private static void printResults(int nVars, int numberOfIterations, double[] endPt) {
        System.out.println(
                "\n\n\nHOOKE USED " + numberOfIterations + " ITERATIONS, AND RETURNED"
        );

        for (int i = 0; i < nVars; i++) {
            System.out.printf("x[%3d] = %15.7e \n", i, endPt[i]);
        }
    }

    public static void mockExperimentalData(SumMeanSquaredErrorsObjectiveFunction aproximationSimple) {
        mockDeformations(aproximationSimple);
        mockExperimentalStress(aproximationSimple);
    }

    private static void mockDeformations(SumMeanSquaredErrorsObjectiveFunction aproximationSimple) {
        double deformationMultiplier = 0.1;
        for (int deformationNumber = 0; deformationNumber < aproximationSimple.NUMBER_OF_POINTS;
             deformationNumber++) {
            aproximationSimple.getDeformations().add(deformationMultiplier * deformationNumber);
        }
    }

    private static void mockExperimentalStress(SumMeanSquaredErrorsObjectiveFunction aproximationSimple) {
        double stressMultiplier = 0.45;
        for (int stressNumber = 0; stressNumber < aproximationSimple.NUMBER_OF_POINTS; stressNumber++) {
            aproximationSimple.getExperimentalStresses().add(stressNumber * stressMultiplier);
        }
    }

}
