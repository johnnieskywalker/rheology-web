package optimization.nonlinear.unconstrained.examples;

import calculations.OptimizedValuesToTableWrappersConverter;
import dataloaders.TableWrappersToSumMeanSquaredErrorsReader;
import optimization.nonlinear.unconstrained.core.HookeAlgorithm;
import optimization.nonlinear.unconstrained.core.SearchMethod;
import optimization.nonlinear.unconstrained.core.SumRootMeanSquaredErrorsObjectiveFunction;
import optimization.nonlinear.unconstrained.core.materialFunctions.CompressedMaterialWithoutRecrystalizationSoftening;
import optimization.nonlinear.unconstrained.core.materialFunctions.MaterialFunction;
import optimization.nonlinear.unconstrained.core.materialFunctions.SimpleMaterialFunction;
import settings.CompressedMaterialFunctionSettings;
import settings.MaterialFunctionType;
import settings.SettingsProvider;
import settings.SimpleMaterialFunctionSettings;
import view.wrappers.TableRowWrapper;

import java.util.List;

public class ApproximationFromTableWrappersTask {

    private OptimizedValuesToTableWrappersConverter optimizedValuesToTableWrappersConverter = new OptimizedValuesToTableWrappersConverter();

    private SearchMethod searchMethod = new HookeAlgorithm();

    private SumRootMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction = new SumRootMeanSquaredErrorsObjectiveFunction();

//    private CompressedMaterialFunctionSettings compressedMaterialFunctionSettings = new
//            CompressedMaterialFunctionSettings();

    SettingsProvider settingsProvider = new SettingsProvider();

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
//            startPoint[HookeAlgorithm.INDEX_ZERO] = ConstantValues.STARTING_K_VALUE;
//            startPoint[HookeAlgorithm.INDEX_ONE] = ConstantValues.STARTING_N_VALUE;
            runHookeJeeves();
        } else {
//            if (searchMethod instanceof SimpleMaterialFunction) {
//                startPoint[0] = 140.0;
//                startPoint[1] = 10.0;
//            }
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
            sumMeanSquaredErrorsObjectiveFunction.setMaterialFunction(simpleMaterialFunction);

            initSimpleSearchPoints(simpleMaterialFunction);

        } else if (sumMeanSquaredErrorsObjectiveFunction.getMaterialFunction().getType().equals(MaterialFunctionType
                .SIMPLE)) {
            initSimpleSearchPoints((SimpleMaterialFunction) sumMeanSquaredErrorsObjectiveFunction.getMaterialFunction
                    ());
        } else if (sumMeanSquaredErrorsObjectiveFunction.getMaterialFunction().getType().equals(MaterialFunctionType.COMPRESSED)) {
            CompressedMaterialWithoutRecrystalizationSoftening compressedMaterialWithoutRecrystalizationSoftening =
                    (CompressedMaterialWithoutRecrystalizationSoftening) sumMeanSquaredErrorsObjectiveFunction
                            .getMaterialFunction();

            CompressedMaterialFunctionSettings compressedMaterialFunctionSettings =
                    (CompressedMaterialFunctionSettings) settingsProvider
                            .getMaterialFunctionSettingsMap().get(searchMethod.getType()).get
                                    (sumMeanSquaredErrorsObjectiveFunction.getMaterialFunction().getType());

            double startingR0 = compressedMaterialFunctionSettings.getStartingR0();
            double startingK0 = compressedMaterialFunctionSettings.getStartingK0();
            double startingN = compressedMaterialFunctionSettings.getStartingN();
            double startingBeta = compressedMaterialFunctionSettings.getStartingBeta();
            double startingKs = compressedMaterialFunctionSettings.getStartingKs();
            double startingBetas = compressedMaterialFunctionSettings.getStartingBetas();
            double startingM = compressedMaterialFunctionSettings.getStartingM();

            startPoint = new double[7];
            startPoint[0] = startingR0;
            startPoint[1] = startingK0;
            startPoint[2] = startingN;
            startPoint[3] = startingBeta;
            startPoint[4] = startingKs;
            startPoint[5] = startingBetas;
            startPoint[6] = startingM;

            double processTemperature = compressedMaterialFunctionSettings.getProcessTemperature();
            double processStrainRate = compressedMaterialFunctionSettings.getProcessStrainRate();


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

    private void initSimpleSearchPoints(SimpleMaterialFunction simpleMaterialFunction) {
        SimpleMaterialFunctionSettings simpleMaterialFunctionSettings = (SimpleMaterialFunctionSettings)
                settingsProvider
                        .getMaterialFunctionSettingsMap().get(searchMethod.getType()).get
                        (simpleMaterialFunction.getType());

        startPoint[0] = simpleMaterialFunctionSettings.getStartingKValue();
        startPoint[1] = simpleMaterialFunctionSettings.getStartingNValue();

        simpleMaterialFunction.setParameterK(startPoint[0]);
        simpleMaterialFunction.setParameterN(startPoint[1]);
    }

    public void setTableRowWrappers(List<TableRowWrapper> tableRowWrappers) {
        optimizedValuesToTableWrappersConverter.setTableRowWrappers(tableRowWrappers);
    }

    public List<TableRowWrapper> loadResultTableRowWrappers() {
        optimizedValuesToTableWrappersConverter.setObjectiveFunction(sumMeanSquaredErrorsObjectiveFunction);
//        optimizedValuesToTableWrappersConverter.setOptimizedParameterK(getOptimizedParameterKValue());
//        optimizedValuesToTableWrappersConverter.setOptimizedParameterN(getOptimizedParameterNValue());


        optimizedValuesToTableWrappersConverter.fillTableWrappersWithCalculatedValues();
        return optimizedValuesToTableWrappersConverter.getTableRowWrappers();
    }

    public void setMaterialFunction(MaterialFunction materialFunction) {
        sumMeanSquaredErrorsObjectiveFunction.setMaterialFunction(materialFunction);
    }

    public double getObjectiveFunctionValue() {
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

    public double[] getResultPoints() {
        return resultPoints;
    }

    public void setResultPoints(double[] resultPoints) {
        this.resultPoints = resultPoints;
    }

    public MaterialFunctionType getMaterialFunctionType() {
        return sumMeanSquaredErrorsObjectiveFunction.getMaterialFunction().getType();
    }
}
