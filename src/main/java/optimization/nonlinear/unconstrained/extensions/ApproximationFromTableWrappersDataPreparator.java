package optimization.nonlinear.unconstrained.extensions;

import optimization.nonlinear.unconstrained.core.ApproximationFromFileTask;
import optimization.nonlinear.unconstrained.core.SumMeanSquaredErrorsObjectiveFunction;
import view.wrappers.TableRowWrapper;

import java.util.List;

public class ApproximationFromTableWrappersDataPreparator extends ApproximationFromFileTask {


    public void loadExperimentalDataFromTableWrappers(List<TableRowWrapper> tableRowWrappers){
        tableRowWrappers.forEach( tableRowWrapper -> {
            addExperimantalStress(tableRowWrapper.getExperimentalStress());
            addDeformation(tableRowWrapper.getDeformation());
        });
    }

    public void addExperimantalStress(Double experimentalStress){
        sumMeanSquaredErrorsObjectiveFunction.getExperimentalStresses().add(experimentalStress);
    }

    public void addDeformation(Double deformation){
        sumMeanSquaredErrorsObjectiveFunction.getDeformations().add(deformation);
    }

    @Override
    public SumMeanSquaredErrorsObjectiveFunction getSumMeanSquaredErrorsObjectiveFunction() {
        return super.getSumMeanSquaredErrorsObjectiveFunction();
    }
}
