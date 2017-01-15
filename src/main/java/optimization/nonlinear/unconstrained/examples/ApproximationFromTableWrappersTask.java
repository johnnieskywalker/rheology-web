package optimization.nonlinear.unconstrained.examples;

import optimization.nonlinear.unconstrained.core.HookeAlgorithm;
import optimization.nonlinear.unconstrained.core.SumMeanSquaredErrorsObjectiveFunction;
import view.wrappers.TableRowWrapper;

import java.util.List;

public class ApproximationFromTableWrappersTask {

    ApproximationFromTableWrappersTask approximationFromTableWrappersTask = new ApproximationFromTableWrappersTask();

    List<TableRowWrapper> tableRowWrappers;

    public void run() {

        int numberOfVariables = 2;
        double rho = SumMeanSquaredErrorsObjectiveFunction.STRPSIZE_GEOMETRIC_SHRINK_RHO;
        double epsilon = HookeAlgorithm.ENDING_VALUE_OF_STEPSIZE;
        double[] resultPoints = new double[HookeAlgorithm.MAXIMUM_NUMBER_OF_VARIABLES];
        double[] startPoint = new double[HookeAlgorithm.MAXIMUM_NUMBER_OF_VARIABLES];
        startPoint[HookeAlgorithm.INDEX_ZERO] = 0.1;
        startPoint[HookeAlgorithm.INDEX_ONE] = 0.1;

        loadExperimentalData();

//        Hooke hooke = new Hooke();
        //FIXME - ŁW doprowadz to do ladu zeby sie dalo wynik wykorzystac w tabelce
//        hooke.findMinimum(
//                numberOfVariables, startPoint, resultPoints, rho, epsilon, Hooke.MAXIMUM_NUMBER_OF_ITERATIONS,
//                approximationFromTableWrappersTask.getSumMeanSquaredErrorsObjectiveFunction());
//        int numberOfIterations = hooke.getNumberOfIterations();

//        printResults(numberOfVariables, numberOfIterations, resultPoints);
    }

    public  void loadExperimentalData() {
        approximationFromTableWrappersTask.
        approximationFromTableWrappersTask.loadExperimentalData();
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
