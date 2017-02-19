package optimization.nonlinear.unconstrained.examples;

import optimization.nonlinear.unconstrained.core.SumRootMeanSquaredErrorsObjectiveFunction;
import optimization.nonlinear.unconstrained.core.HookeAlgorithm;

public class ApproximationSimpleUse {


    public static void main(final String[] args) {

        SumRootMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction = new SumRootMeanSquaredErrorsObjectiveFunction();


        HookeAlgorithm hookeAlgorithm = new HookeAlgorithm();

        mockExperimentalData(sumMeanSquaredErrorsObjectiveFunction);

        int nVars = 2;
        int iterMax = HookeAlgorithm.MAXIMUM_NUMBER_OF_ITERATIONS;
        double rho = sumMeanSquaredErrorsObjectiveFunction.STRPSIZE_GEOMETRIC_SHRINK_RHO;
        double epsilon = HookeAlgorithm.ENDING_VALUE_OF_STEPSIZE;
        double[] endPt = new double[HookeAlgorithm.MAXIMUM_NUMBER_OF_VARIABLES];
        double[] startPt = new double[HookeAlgorithm.MAXIMUM_NUMBER_OF_VARIABLES];
        startPt[HookeAlgorithm.INDEX_ZERO] = 0.1;
        startPt[HookeAlgorithm.INDEX_ONE] = 0.1;

        hookeAlgorithm.findMinimum(
                nVars, startPt, endPt, rho, epsilon, iterMax, sumMeanSquaredErrorsObjectiveFunction);
        int numberOfIterations = hookeAlgorithm.getNumberOfIterations();

        printResults(nVars, numberOfIterations, endPt);
        System.out.println("\n\nin hooke object:");
        printResults(nVars, numberOfIterations, hookeAlgorithm.getResultPointCoordinates());
    }

    private static void printResults(int nVars, int numberOfIterations, double[] endPt) {
        System.out.println(
                "\n\n\nHOOKE USED " + numberOfIterations + " ITERATIONS, AND RETURNED"
        );

        for (int i = 0; i < nVars; i++) {
            System.out.printf("x[%3d] = %15.7e \n", i, endPt[i]);
        }
    }

    public static void mockExperimentalData(SumRootMeanSquaredErrorsObjectiveFunction aproximationSimple) {
        mockDeformations(aproximationSimple);
        mockExperimentalStress(aproximationSimple);
    }

    private static void mockDeformations(SumRootMeanSquaredErrorsObjectiveFunction aproximationSimple) {
        double deformationMultiplier = 0.1;
        for (int deformationNumber = 0; deformationNumber < aproximationSimple.NUMBER_OF_POINTS;
             deformationNumber++) {
            aproximationSimple.getDeformations().add(deformationMultiplier * deformationNumber);
        }
    }

    private static void mockExperimentalStress(SumRootMeanSquaredErrorsObjectiveFunction aproximationSimple) {
        double stressMultiplier = 0.45;
        for (int stressNumber = 0; stressNumber < aproximationSimple.NUMBER_OF_POINTS; stressNumber++) {
            aproximationSimple.getExperimentalStresses().add(stressNumber * stressMultiplier);
        }
    }

}
