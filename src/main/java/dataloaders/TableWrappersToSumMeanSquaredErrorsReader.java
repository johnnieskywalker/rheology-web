package dataloaders;

import optimization.nonlinear.unconstrained.core.SumRootMeanSquaredErrorsObjectiveFunction;
import view.wrappers.TableRowWrapper;

import java.util.List;

public class TableWrappersToSumMeanSquaredErrorsReader {

    public static SumRootMeanSquaredErrorsObjectiveFunction read(List<TableRowWrapper> tableRowWrappers) {
        SumRootMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction = new SumRootMeanSquaredErrorsObjectiveFunction();

        tableRowWrappers.forEach(tableRowWrapper -> {
            sumMeanSquaredErrorsObjectiveFunction.addDeformation(tableRowWrapper.getDeformation());
            sumMeanSquaredErrorsObjectiveFunction.addExperimentalStress(tableRowWrapper.getExperimentalStress());
        });

        return sumMeanSquaredErrorsObjectiveFunction;
    }

}
