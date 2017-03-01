package optimization.nonlinear.unconstrained.examples;

import calculations.OptimizedValuesToTableWrappersConverter;
import dataloaders.TableWrappersToSumMeanSquaredErrorsReader;
import optimization.nonlinear.unconstrained.core.HookeAlgorithm;
import optimization.nonlinear.unconstrained.core.SearchMethod;
import optimization.nonlinear.unconstrained.core.SumRootMeanSquaredErrorsObjectiveFunction;
import optimization.nonlinear.unconstrained.core.materialFunctions.CompressedMaterialWithoutRecrystalizationSoftening;
import optimization.nonlinear.unconstrained.core.materialFunctions.MaterialFunction;
import optimization.nonlinear.unconstrained.core.materialFunctions.SimpleMaterialFunction;
import utils.ConstantValues;
import view.wrappers.TableRowWrapper;

import java.util.List;

public class ApproximationFromTableWrappersTask {

    private OptimizedValuesToTableWrappersConverter optimizedValuesToTableWrappersConverter = new OptimizedValuesToTableWrappersConverter();

    private SearchMethod searchMethod = new HookeAlgorithm();

    private SumRootMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction = new SumRootMeanSquaredErrorsObjectiveFunction();

//    private int numberOfVariables = 2;
    private double rho = SumRootMeanSquaredErrorsObjectiveFunction.STRPSIZE_GEOMETRIC_SHRINK_RHO;
    private double epsilon = HookeAlgorithm.ENDING_VALUE_OF_STEPSIZE;
    private double[] resultPoints = new double[HookeAlgorithm.MAXIMUM_NUMBER_OF_VARIABLES];
    private double[] startPoint;


    public void run() {


        TableWrappersToSumMeanSquaredErrorsReader tableWrappersToSumMeanSquaredErrorsReader = new
                TableWrappersToSumMeanSquaredErrorsReader(sumMeanSquaredErrorsObjectiveFunction);

        sumMeanSquaredErrorsObjectiveFunction =
                tableWrappersToSumMeanSquaredErrorsReader.read(optimizedValuesToTableWrappersConverter.getTableRowWrappers());

        initStartPoints();

        if (searchMethod instanceof HookeAlgorithm) {
            startPoint[HookeAlgorithm.INDEX_ZERO] = ConstantValues.STARTING_K_VALUE;
            startPoint[HookeAlgorithm.INDEX_ONE] = ConstantValues.STARTING_N_VALUE;
            runHookeJeeves();
        } else {
            if(searchMethod instanceof SimpleMaterialFunction) {
                startPoint[0] = 140.0;
                startPoint[1] = 10.0;
            }
            searchMethod.setCurrentSearchPoints(startPoint);
            searchMethod.findMinimum(sumMeanSquaredErrorsObjectiveFunction);
            resultPoints = searchMethod.getResultPointCoordinates();
        }
    }

    private void runHookeJeeves() {

        HookeAlgorithm hookeAlgorithm = new HookeAlgorithm();
        hookeAlgorithm.findMinimum(
                startPoint.length, startPoint, resultPoints, rho, epsilon, HookeAlgorithm.MAXIMUM_NUMBER_OF_ITERATIONS,
                sumMeanSquaredErrorsObjectiveFunction);
    }

    private void initStartPoints() {
        if (sumMeanSquaredErrorsObjectiveFunction.getMaterialFunction() == null) {
            if (startPoint == null) {
                startPoint = new double[2];
            }

            searchMethod.setCurrentSearchPoints(startPoint);
            SimpleMaterialFunction simpleMaterialFunction = new SimpleMaterialFunction();
            simpleMaterialFunction.setParameterK(startPoint[0]);
            simpleMaterialFunction.setParameterN(startPoint[1]);
            sumMeanSquaredErrorsObjectiveFunction.setMaterialFunction(simpleMaterialFunction);
        }
        else if (sumMeanSquaredErrorsObjectiveFunction.getMaterialFunction() instanceof CompressedMaterialWithoutRecrystalizationSoftening){
            CompressedMaterialWithoutRecrystalizationSoftening compressedMaterialWithoutRecrystalizationSoftening =
                    (CompressedMaterialWithoutRecrystalizationSoftening) sumMeanSquaredErrorsObjectiveFunction
            .getMaterialFunction();

            double startingR0 = 1.0;
            double startingK0 = 4.0;
            double startingN = 1.0;
            double startingBeta = 1.0;
            double startingKs = 1.0;
            double startingBetas = 1.0;
            double startingM = 6.0;

            startPoint=new double[7];
            startPoint[0]=startingR0;
            startPoint[1]=startingK0;
            startPoint[2]=startingN;
            startPoint[3]=startingBeta;
            startPoint[4]=startingKs;
            startPoint[5]=startingBetas;
            startPoint[6]=startingM;


            double processTemperature = 1200.0;
            double processStrainRate = 1.0;


            compressedMaterialWithoutRecrystalizationSoftening.setR0(startingR0);
            compressedMaterialWithoutRecrystalizationSoftening.setK0(startingK0);
            compressedMaterialWithoutRecrystalizationSoftening.setN(startingN);
            compressedMaterialWithoutRecrystalizationSoftening.setBeta(startingBeta);
            compressedMaterialWithoutRecrystalizationSoftening.setKs(startingKs);
            compressedMaterialWithoutRecrystalizationSoftening.setBetas(startingBetas);
            compressedMaterialWithoutRecrystalizationSoftening.setM(startingM);

            compressedMaterialWithoutRecrystalizationSoftening.setProcessTemperature(processTemperature);
            compressedMaterialWithoutRecrystalizationSoftening.setProcessStrainRate(processStrainRate);

            sumMeanSquaredErrorsObjectiveFunction.setMaterialFunction(compressedMaterialWithoutRecrystalizationSoftening);
        }
    }

    public void setTableRowWrappers(List<TableRowWrapper> tableRowWrappers) {
        optimizedValuesToTableWrappersConverter.setTableRowWrappers(tableRowWrappers);
    }

    public List<TableRowWrapper> loadResultTableRowWrappers() {
        optimizedValuesToTableWrappersConverter.setObjectiveFunction(sumMeanSquaredErrorsObjectiveFunction);
        optimizedValuesToTableWrappersConverter.setOptimizedParameterK(getOptimizedParameterKValue());
        optimizedValuesToTableWrappersConverter.setOptimizedParameterN(getOptimizedParameterNValue());


        optimizedValuesToTableWrappersConverter.fillTableWrappersWithCalculatedValues();
        return optimizedValuesToTableWrappersConverter.getTableRowWrappers();
    }

    public void setMaterialFunction(MaterialFunction materialFunction) {
        sumMeanSquaredErrorsObjectiveFunction.setMaterialFunction(materialFunction);
    }

    public double getSumMeanSquaredErrorsValue() {
        return optimizedValuesToTableWrappersConverter.getSumMeanSquaredErrorsValue();
    }

    public double getOptimizedParameterKValue() {
        return resultPoints[0];
    }

    public double getOptimizedParameterNValue() {
        return resultPoints[1];
    }

    public SearchMethod getSearchMethod() {
        return searchMethod;
    }

    public void setSearchMethod(SearchMethod searchMethod) {
        this.searchMethod = searchMethod;
    }

    public double[] getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(double[] startPoint) {
        this.startPoint = startPoint;
    }
}
