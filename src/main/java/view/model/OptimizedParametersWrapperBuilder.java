package view.model;

import optimization.nonlinear.unconstrained.examples.ApproximationFromTableWrappersTask;
import settings.MaterialFunctionType;

import java.util.ArrayList;
import java.util.List;

public class OptimizedParametersWrapperBuilder {
    private ApproximationFromTableWrappersTask approximationFromTableWrappersTask;

    private List<OptimizedParameterWrapper> optimizedParameterWrappers=new ArrayList<>();

    public OptimizedParametersWrapperBuilder(ApproximationFromTableWrappersTask approximationFromTableWrappersTask) {
        this.approximationFromTableWrappersTask = approximationFromTableWrappersTask;
    }

    public void reloadOptimizedParameters(){
        optimizedParameterWrappers.clear();
        if (approximationFromTableWrappersTask.getMaterialFunctionType().equals(MaterialFunctionType.SIMPLE)){
            optimizedParameterWrappers.add(new OptimizedParameterWrapper("k",approximationFromTableWrappersTask
                    .getStartPoint()[0],approximationFromTableWrappersTask.getResultPoints()[0]));
            optimizedParameterWrappers.add(new OptimizedParameterWrapper("n",approximationFromTableWrappersTask
                    .getStartPoint()[1],approximationFromTableWrappersTask.getResultPoints()[1]));
        }
        if (approximationFromTableWrappersTask.getMaterialFunctionType().equals(MaterialFunctionType.COMPRESSED)){
//            startPoint = new double[7];
//            startPoint[0] = startingR0;
//            startPoint[1] = startingK0;
//            startPoint[2] = startingN;
//            startPoint[3] = startingBeta;
//            startPoint[4] = startingKs;
//            startPoint[5] = startingBetas;
//            startPoint[6] = startingM;
            optimizedParameterWrappers.add(new OptimizedParameterWrapper("R0",approximationFromTableWrappersTask
                    .getStartPoint()[0],approximationFromTableWrappersTask.getResultPoints()[0]));
            optimizedParameterWrappers.add(new OptimizedParameterWrapper("K0",approximationFromTableWrappersTask
                    .getStartPoint()[1],approximationFromTableWrappersTask.getResultPoints()[1]));
            optimizedParameterWrappers.add(new OptimizedParameterWrapper("n",approximationFromTableWrappersTask
                    .getStartPoint()[2],approximationFromTableWrappersTask.getResultPoints()[2]));
            optimizedParameterWrappers.add(new OptimizedParameterWrapper("Beta",approximationFromTableWrappersTask
                    .getStartPoint()[3],approximationFromTableWrappersTask.getResultPoints()[3]));
            optimizedParameterWrappers.add(new OptimizedParameterWrapper("Ks",approximationFromTableWrappersTask
                    .getStartPoint()[4],approximationFromTableWrappersTask.getResultPoints()[4]));
            optimizedParameterWrappers.add(new OptimizedParameterWrapper("Betas",approximationFromTableWrappersTask
                    .getStartPoint()[5],approximationFromTableWrappersTask.getResultPoints()[5]));
            optimizedParameterWrappers.add(new OptimizedParameterWrapper("m",approximationFromTableWrappersTask
                    .getStartPoint()[6],approximationFromTableWrappersTask.getResultPoints()[6]));
        }
    }

    public String getParametersInfo(){
        StringBuilder parametersInfo = new StringBuilder("");
    optimizedParameterWrappers.forEach(optimizedParameterWrapper -> {
        parametersInfo.append(optimizedParameterWrapper.getName()+" początkowa wartość : "+optimizedParameterWrapper
                .getInitialValue()+" wartość po optymalizacji : "+optimizedParameterWrapper
                .getResultValue()+" "+System.getProperty("line.separator"));
    });
        return parametersInfo.toString();
    }

    public List<OptimizedParameterWrapper> getOptimizedParameterWrappers() {
        return optimizedParameterWrappers;
    }
}
