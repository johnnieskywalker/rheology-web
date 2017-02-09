package tests;

import optimization.nonlinear.unconstrained.core.ApproximationFromFileTask;
import optimization.nonlinear.unconstrained.core.HookeAlgorithm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HookeAlgorithmTest {

    ApproximationFromFileTask approximationFromFileTask;

    @Before
    public void setUp(){
        approximationFromFileTask = new ApproximationFromFileTask();
    }

    @Test
    public void shouldFindMinimumOfSimpleFunctionCloseToExcelSolverSolution(){
        int numberOfVariables = 2;
        double rho = approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction().STRPSIZE_GEOMETRIC_SHRINK_RHO;
        double epsilon = HookeAlgorithm.ENDING_VALUE_OF_STEPSIZE;
        double[] resultPoints = new double[HookeAlgorithm.MAXIMUM_NUMBER_OF_VARIABLES];
        double[] startPoint = new double[HookeAlgorithm.MAXIMUM_NUMBER_OF_VARIABLES];
        startPoint[HookeAlgorithm.INDEX_ZERO] = 0.1;
        startPoint[HookeAlgorithm.INDEX_ONE] = 0.1;

        loadExperimentalData(approximationFromFileTask);

        HookeAlgorithm hookeAlgorithm = new HookeAlgorithm();
        hookeAlgorithm.findMinimum(
                numberOfVariables, startPoint, resultPoints, rho, epsilon, HookeAlgorithm.MAXIMUM_NUMBER_OF_ITERATIONS,
                approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction());
        int numberOfIterations = hookeAlgorithm.getNumberOfIterations();

        printResults(numberOfVariables, numberOfIterations, resultPoints);

        double allowedDifferenceBetweenResultFromExcelForK = 1.0;
        assertEquals(144.688103141454,resultPoints[0],allowedDifferenceBetweenResultFromExcelForK);
        double allowedDifferenceBetweenResultFromExcelForN = 0.003;
        assertEquals(0.237095895731918,resultPoints[1],allowedDifferenceBetweenResultFromExcelForN);
    }

    public static void loadExperimentalData(ApproximationFromFileTask approximationFromFileTask) {
        approximationFromFileTask.loadExperimentalDataFromFilesPaths
                ("optimizationTasksData/kperzyns_aproksymacja_data/taskData/experimentalStress",
                        "optimizationTasksData/kperzyns_aproksymacja_data/taskData/experimentalDeformation");
    }

    private static void printResults(int numberOfVariables, int numberOfIterations, double[] resultPoints) {
        System.out.println(
                "\nHOOKE USED " + numberOfIterations + " ITERATIONS, AND RETURNED"
        );

        for (int i = 0; i < numberOfVariables; i++) {
            System.out.printf("x[%3d] = %15.7e \n", i, resultPoints[i]);
        }
    }

}
