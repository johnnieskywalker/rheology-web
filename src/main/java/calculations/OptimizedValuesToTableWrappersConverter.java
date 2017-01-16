package calculations;

import optimization.nonlinear.unconstrained.core.ObjectiveFunction;
import view.wrappers.TableRowWrapper;

import java.util.List;

public class OptimizedValuesToTableWrappersConverter {

    private double optimizedParameterK;

    private double optimizedParameterN;

    private ObjectiveFunction objectiveFunction;

    private List<TableRowWrapper> tableRowWrappers;

    public void fillTableWrappersWithCalculatedValues() {
        tableRowWrappers.forEach(tableRowWrapper -> {
            fillCalculatedStressInRow(tableRowWrapper);
            fillMeanSquaredErrorInRow(tableRowWrapper);
        });
    }

    private void fillCalculatedStressInRow(TableRowWrapper tableRowWrapper) {
        tableRowWrapper.setCalculatedStress(objectiveFunction.calculateMaterialStressInPoint
                (optimizedParameterK, optimizedParameterN, tableRowWrapper.getDeformation()));
    }

    private void fillMeanSquaredErrorInRow(TableRowWrapper tableRowWrapper) {
        tableRowWrapper.setMeanSquaredError(objectiveFunction.meanSquaredError(tableRowWrapper.getExperimentalStress(),
                tableRowWrapper.getCalculatedStress()));
    }

    public double getSumMeanSquaredErrorsValue() {
        double sumMeanSquaredErrors = 0.0;
        for (TableRowWrapper tableRowWrapper : tableRowWrappers) {
            sumMeanSquaredErrors+=tableRowWrapper.getMeanSquaredError();
        }
        return sumMeanSquaredErrors;
    }

    public double getOptimizedParameterK() {
        return optimizedParameterK;
    }

    public void setOptimizedParameterK(double optimizedParameterK) {
        this.optimizedParameterK = optimizedParameterK;
    }

    public double getOptimizedParameterN() {
        return optimizedParameterN;
    }

    public void setOptimizedParameterN(double optimizedParameterN) {
        this.optimizedParameterN = optimizedParameterN;
    }

    public ObjectiveFunction getObjectiveFunction() {
        return objectiveFunction;
    }

    public void setObjectiveFunction(ObjectiveFunction objectiveFunction) {
        this.objectiveFunction = objectiveFunction;
    }

    public List<TableRowWrapper> getTableRowWrappers() {
        return tableRowWrappers;
    }

    public void setTableRowWrappers(List<TableRowWrapper> tableRowWrappers) {
        this.tableRowWrappers = tableRowWrappers;
    }
}
