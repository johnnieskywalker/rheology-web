package optimization.nonlinear.unconstrained.core;

import de.xypron.jcobyla.Calcfc;
import de.xypron.jcobyla.Cobyla;
import de.xypron.jcobyla.CobylaExitStatus;

public class PowellAlgorithm implements SearchMethod {

    double[] startingPoints;

    @Override
    public void findMinimum(ObjectiveFunction objectiveFunction) {
        Calcfc calcfc = new Calcfc() {
            @Override
            public double compute(int n, int m, double[] x, double[] con) {
                return objectiveFunction.findValueForArguments(x);
            }
        };


        int maxNumberOfIterations=50;
        int printLevel = 1;
        CobylaExitStatus result = Cobyla.findMinimum(calcfc, startingPoints.length, 0, startingPoints, 0.3, 0.5, printLevel, maxNumberOfIterations);
    }

    @Override
    public double[] getResultPointCoordinates() {
        return startingPoints;
    }

    @Override
    public int getNumberOfIterations() {
        //TODO - ŁW zwracac nr iteracji z cobyly
        return 0;
    }

    public void setStartingPoints(double[] startingPoints){
        this.startingPoints = startingPoints;
    }
}
