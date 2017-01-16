package dataloaders;

import optimization.nonlinear.unconstrained.core.SumMeanSquaredErrorsObjectiveFunction;
import view.wrappers.TableRowWrapper;

import java.util.List;

public class TableWrappersToSumMeanSquaredErrorsReader {

    public static SumMeanSquaredErrorsObjectiveFunction read(List<TableRowWrapper> tableRowWrappers) {
        SumMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction = new SumMeanSquaredErrorsObjectiveFunction();

        tableRowWrappers.forEach(tableRowWrapper -> {
            sumMeanSquaredErrorsObjectiveFunction.addDeformation(tableRowWrapper.getDeformation());
            sumMeanSquaredErrorsObjectiveFunction.addExperimentalStress(tableRowWrapper.getExperimentalStress());
        });

        return sumMeanSquaredErrorsObjectiveFunction;
    }

}
