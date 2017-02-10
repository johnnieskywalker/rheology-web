package optimization.nonlinear.unconstrained.core;

import de.xypron.jcobyla.Calcfc;
import de.xypron.jcobyla.Cobyla;
import de.xypron.jcobyla.CobylaExitStatus;

public class PowellAlgorithm implements SearchMethod {

    double[] currentSearchPoints;

    @Override
    public void findMinimum(ObjectiveFunction objectiveFunction) {
        Calcfc calcfc = new Calcfc() {
            @Override
            public double compute(int n, int m, double[] x, double[] con) {
//                return objectiveFunction.findValueForArguments(x);
            return    setNewParameterValuesAndFindMaterialFunctionValue(objectiveFunction, x);
            }
        };


        int maxNumberOfIterations=50;
        int printLevel = 1;
        CobylaExitStatus result = Cobyla.findMinimum(calcfc, currentSearchPoints.length, 0, currentSearchPoints, 0.3, 0.5, printLevel, maxNumberOfIterations);
    }
    //TODO - ŁW dla niej tez by sie przydal test jak dla hooke, bo jak narazie testuje czystą Cobyla
    @Override
    public double[] getResultPointCoordinates() {
        return currentSearchPoints;
    }

    @Override
    public int getNumberOfIterations() {
        //TODO - ŁW zwracac nr iteracji z cobyly
        return 0;
    }

    @Override
    public double setNewParameterValuesAndFindMaterialFunctionValue(ObjectiveFunction objectiveFunction, double[]
            newFunctionArguments) {
        objectiveFunction.getMaterialFunction().setNewOptimizedParameterValues(newFunctionArguments);
        return objectiveFunction.findValueForArguments(newFunctionArguments);
    }

    public void setCurrentSearchPoints(double[] currentSearchPoints){
        this.currentSearchPoints = currentSearchPoints;
    }
}
