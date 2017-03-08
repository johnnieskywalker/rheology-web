package calculations;

import optimization.nonlinear.unconstrained.core.ObjectiveFunction;
import view.wrappers.TableRowWrapper;

import java.util.List;

public class OptimizedValuesToTableWrappersConverter {

    private ObjectiveFunction objectiveFunction;

    private List<TableRowWrapper> tableRowWrappers;

    public void fillTableWrappersWithCalculatedValues() {
        //TODO: tu sie chyba liczy to zawsze z podstawowego wzoru, podmieniac to w zaleznosciod wybranego modelu
        // materialu
        tableRowWrappers.forEach(tableRowWrapper -> {
            fillCalculatedStressInRow(tableRowWrapper);
            fillMeanSquaredErrorInRow(tableRowWrapper);
        });
    }

    private void fillCalculatedStressInRow(TableRowWrapper tableRowWrapper) {
        tableRowWrapper.setCalculatedStress(objectiveFunction.calculateMaterialStressInPoint(tableRowWrapper
                .getDeformation()));
    }

    private void fillMeanSquaredErrorInRow(TableRowWrapper tableRowWrapper) {
        tableRowWrapper.setMeanSquaredError(objectiveFunction.rootMeanSquaredError(tableRowWrapper.getExperimentalStress(),
                tableRowWrapper.getCalculatedStress()));
    }

    public double getSumMeanSquaredErrorsValue() {
        double sumMeanSquaredErrors = 0.0;
        for (TableRowWrapper tableRowWrapper : tableRowWrappers) {
            sumMeanSquaredErrors += tableRowWrapper.getMeanSquaredError();
        }
        return sumMeanSquaredErrors;
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
