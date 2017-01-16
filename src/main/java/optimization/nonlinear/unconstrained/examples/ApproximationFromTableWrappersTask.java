package optimization.nonlinear.unconstrained.examples;

import calculations.OptimizedValuesToTableWrappersConverter;
import dataloaders.TableWrappersToSumMeanSquaredErrorsReader;
import optimization.nonlinear.unconstrained.core.HookeAlgorithm;
import optimization.nonlinear.unconstrained.core.SumMeanSquaredErrorsObjectiveFunction;
import view.wrappers.TableRowWrapper;

import java.util.List;

public class ApproximationFromTableWrappersTask {

    private OptimizedValuesToTableWrappersConverter optimizedValuesToTableWrappersConverter = new OptimizedValuesToTableWrappersConverter();

    private HookeAlgorithm hookeAlgorithm = new HookeAlgorithm();

    private SumMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction;

    private int numberOfVariables = 2;
    private double rho = SumMeanSquaredErrorsObjectiveFunction.STRPSIZE_GEOMETRIC_SHRINK_RHO;
    private double epsilon = HookeAlgorithm.ENDING_VALUE_OF_STEPSIZE;
    private double[] resultPoints = new double[HookeAlgorithm.MAXIMUM_NUMBER_OF_VARIABLES];
    private double[] startPoint = new double[HookeAlgorithm.MAXIMUM_NUMBER_OF_VARIABLES];


    public void run() {

        initStartPoints();

        sumMeanSquaredErrorsObjectiveFunction =
        TableWrappersToSumMeanSquaredErrorsReader.read(optimizedValuesToTableWrappersConverter.getTableRowWrappers());


        //FIXME - ŁW doprowadz to do ladu zeby sie dalo wynik wykorzystac w tabelce wykorzystac OptimizedValuesToTableWrappersConverter

        hookeAlgorithm.findMinimum(
                numberOfVariables, startPoint, resultPoints, rho, epsilon, HookeAlgorithm.MAXIMUM_NUMBER_OF_ITERATIONS,
                sumMeanSquaredErrorsObjectiveFunction);
        int numberOfIterations = hookeAlgorithm.getNumberOfIterations();

        printResults(numberOfVariables, numberOfIterations, resultPoints);
    }

    private void initStartPoints() {
        startPoint[HookeAlgorithm.INDEX_ZERO] = 0.1;
        startPoint[HookeAlgorithm.INDEX_ONE] = 0.1;
    }

    public void setTableRowWrappers(List<TableRowWrapper> tableRowWrappers){
        optimizedValuesToTableWrappersConverter.setTableRowWrappers(tableRowWrappers);
    }

    public List<TableRowWrapper> loadResultTableRowWrappers(){
        optimizedValuesToTableWrappersConverter.setObjectiveFunction(sumMeanSquaredErrorsObjectiveFunction);
        optimizedValuesToTableWrappersConverter.setOptimizedParameterK(getOptimizedParameterKValue());
        optimizedValuesToTableWrappersConverter.setOptimizedParameterN(getOptimizedParameterNValue());


        optimizedValuesToTableWrappersConverter.fillTableWrappersWithCalculatedValues();
        return optimizedValuesToTableWrappersConverter.getTableRowWrappers();
    }

    public double getOptimizedParameterKValue(){
        return resultPoints[0];
    }

    public double getOptimizedParameterNValue(){
        return resultPoints[1];
    }
    //FIXME - ŁW wywalic pozniej to
    private static void printResults(int numberOfVariables, int numberOfIterations, double[] resultPoints) {
        System.out.println(
                "\nHOOKE USED " + numberOfIterations + " ITERATIONS, AND RETURNED"
        );

        for (int i = 0; i < numberOfVariables; i++) {
            System.out.printf("x[%3d] = %15.7e \n", i, resultPoints[i]);
        }
    }
}
