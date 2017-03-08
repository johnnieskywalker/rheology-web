package optimization.nonlinear.unconstrained.core;

import settings.SearchMethodType;

public interface SearchMethod {

    /**
     * Default method for finding minimum without passing any search method-specific arguments like starting points
     * of search algorithm or stepsize etc. each implementation of SearchMethod should contain default values of such
     * parameters allowing programmer using it without paying too much attention to details of search method
     * implementation
     *
     * @param objectiveFunction
     */
    public void findMinimum(ObjectiveFunction objectiveFunction);

    /**
     * WARNING!
     * Use only after calling find minimum
     *
     * @return result point coordinates as array of doubles
     */
    public double[] getResultPointCoordinates();

    /**
     * @return number of iterations done in instance of SearchMethod
     */
    public int getNumberOfIterations();

    public void setCurrentSearchPoints(double[] currentSearchPoints);

    public double setNewParameterValuesAndFindMaterialFunctionValue(ObjectiveFunction objectiveFunction, double[]
            newFunctionArguments);

    public SearchMethodType getType();

}
