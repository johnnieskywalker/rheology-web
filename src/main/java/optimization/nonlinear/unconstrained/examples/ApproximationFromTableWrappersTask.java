package optimization.nonlinear.unconstrained.examples;

import calculations.OptimizedValuesToTableWrappersConverter;
import dataloaders.TableWrappersToSumMeanSquaredErrorsReader;
import optimization.nonlinear.unconstrained.core.HookeAlgorithm;
import optimization.nonlinear.unconstrained.core.SearchMethod;
import optimization.nonlinear.unconstrained.core.SumMeanSquaredErrorsObjectiveFunction;
import optimization.nonlinear.unconstrained.core.materialFunctions.MaterialFunction;
import utils.ConstantValues;
import view.wrappers.TableRowWrapper;

import java.util.List;

public class ApproximationFromTableWrappersTask {

    private OptimizedValuesToTableWrappersConverter optimizedValuesToTableWrappersConverter = new OptimizedValuesToTableWrappersConverter();

    private SearchMethod searchMethod = new HookeAlgorithm();

    private SumMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction = new SumMeanSquaredErrorsObjectiveFunction();

    private int numberOfVariables = 2;
    private double rho = SumMeanSquaredErrorsObjectiveFunction.STRPSIZE_GEOMETRIC_SHRINK_RHO;
    private double epsilon = HookeAlgorithm.ENDING_VALUE_OF_STEPSIZE;
    private double[] resultPoints = new double[HookeAlgorithm.MAXIMUM_NUMBER_OF_VARIABLES];
    private double[] startPoint = new double[HookeAlgorithm.MAXIMUM_NUMBER_OF_VARIABLES];


    public void run() {

        initStartPoints();

        sumMeanSquaredErrorsObjectiveFunction =
        TableWrappersToSumMeanSquaredErrorsReader.read(optimizedValuesToTableWrappersConverter.getTableRowWrappers());

        HookeAlgorithm hookeAlgorithm = new HookeAlgorithm();

        hookeAlgorithm.findMinimum(
                numberOfVariables, startPoint, resultPoints, rho, epsilon, HookeAlgorithm.MAXIMUM_NUMBER_OF_ITERATIONS,
                sumMeanSquaredErrorsObjectiveFunction);
    }

    private void initStartPoints() {
        startPoint[HookeAlgorithm.INDEX_ZERO] = ConstantValues.STARTING_K_VALUE;
        startPoint[HookeAlgorithm.INDEX_ONE] = ConstantValues.STARTING_N_VALUE;
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

    public void setMaterialFunction(MaterialFunction materialFunction){
        sumMeanSquaredErrorsObjectiveFunction.setMaterialFunction(materialFunction);
    }

    public double getSumMeanSquaredErrorsValue(){
        return optimizedValuesToTableWrappersConverter.getSumMeanSquaredErrorsValue();
    }

    public double getOptimizedParameterKValue(){
        return resultPoints[0];
    }

    public double getOptimizedParameterNValue(){
        return resultPoints[1];
    }
}
