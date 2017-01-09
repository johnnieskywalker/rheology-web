package optimization.nonlinear.unconstrained.examples;

import optimization.nonlinear.unconstrained.core.ApproximationFromFileTask;
import optimization.nonlinear.unconstrained.core.Hooke;

public class ApproximationKperzynsTask {


    public static void main(final String[] args) {


        ApproximationFromFileTask approximationFromFileTask = new ApproximationFromFileTask();

        loadExperimentalData(approximationFromFileTask);

        int numberOfVariables = 2;
        double rho = approximationFromFileTask.getSumMeanSquaredErrors().RHO_BEGIN;
        double epsilon = Hooke.ENDING_VALUE_OF_STEPSIZE;
        double[] resultPoints = new double[Hooke.MAXIMUM_NUMBER_OF_VARIABLES];
        double[] startPoint = new double[Hooke.MAXIMUM_NUMBER_OF_VARIABLES];
        startPoint[Hooke.INDEX_ZERO] = 0.1;
        startPoint[Hooke.INDEX_ONE] = 0.1;

        Hooke hooke = new Hooke();
        hooke.findMinimum(
                numberOfVariables, startPoint, resultPoints, rho, epsilon, Hooke.MAXIMUM_NUMBER_OF_ITERATIONS,
                approximationFromFileTask.getSumMeanSquaredErrors());
        int numberOfIterations = hooke.getNumberOfIterations();

        printResults(numberOfVariables, numberOfIterations, resultPoints);
    }

    public static void loadExperimentalData(ApproximationFromFileTask approximationFromFileTask) {
        approximationFromFileTask.loadExperimentalDataFromFilesPaths
                ("optimizationTasksData/kperzyns_aproksymacja_data/taskData/experimentalStress",
                        "optimizationTasksData/kperzyns_aproksymacja_data/taskData/experimentalDeformation");
    }

    private static void printResults(int nVars, int numberOfIterations, double[] resultPoints) {
        System.out.println(
                "\nHOOKE USED " + numberOfIterations + " ITERATIONS, AND RETURNED"
        );

        for (int i = 0; i < nVars; i++) {
            System.out.printf("x[%3d] = %15.7e \n", i, resultPoints[i]);
        }
    }

}
