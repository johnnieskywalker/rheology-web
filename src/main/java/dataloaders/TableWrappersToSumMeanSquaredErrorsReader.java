package dataloaders;

import optimization.nonlinear.unconstrained.core.SumRootMeanSquaredErrorsObjectiveFunction;
import view.wrappers.TableRowWrapper;

import java.util.List;

public class TableWrappersToSumMeanSquaredErrorsReader {

    private SumRootMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction;


    public SumRootMeanSquaredErrorsObjectiveFunction read(List<TableRowWrapper> tableRowWrappers) {

        tableRowWrappers.forEach(tableRowWrapper -> {
            sumMeanSquaredErrorsObjectiveFunction.addDeformation(tableRowWrapper.getDeformation());
            sumMeanSquaredErrorsObjectiveFunction.addExperimentalStress(tableRowWrapper.getExperimentalStress());
        });

        return sumMeanSquaredErrorsObjectiveFunction;
    }

    public TableWrappersToSumMeanSquaredErrorsReader(SumRootMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction) {
        this.sumMeanSquaredErrorsObjectiveFunction = sumMeanSquaredErrorsObjectiveFunction;
    }
}
