/*
 * nlp-unconstrained-core/hooke-jeeves/java/src/main/java/
 * optimization/nonlinear/unconstrained/core/Hooke.java
 * ============================================================================
 * Nonlinear Optimization Algorithms Multilang. Version 0.1
 * ============================================================================
 * Nonlinear programming algorithms as the (un-)constrained minimization
 * problems with the focus on their numerical expression using various
 * programming languages.
 *
 * This is the Hooke and Jeeves nonlinear unconstrained minimization algorithm.
 * ============================================================================
 */

package optimization.nonlinear.unconstrained.core;

public class HookeAlgorithm implements SearchMethod {
    public static final int MAXIMUM_NUMBER_OF_VARIABLES = 250;

    public static final double ENDING_VALUE_OF_STEPSIZE = 1E-6;

    public static final int MAXIMUM_NUMBER_OF_ITERATIONS = 5000;

    public static final int INDEX_ZERO = 0;
    public static final int INDEX_ONE = 1;
    private static final double ZERO_POINT_FIVE = 0.5;

    private int numberOfIterations = 0;

    private double[] resultPointCoordinates;

    private int numberOfFunctionEvaluations = 0;

    public static double getZeroPointFive() {
        return ZERO_POINT_FIVE;
    }

    /**
     * Helper method.
     * <br />Given a point, look for a better one nearby, one coord at a time.
     * <br /> Realizing trial phase from book by Kusiak && Danielewska
     *
     * @param stepLengthsForIterations                        The stepLenghtForIteration between <code>previousBestValuedCoordinate</code>
     *                                     and <code>point</code>.
     * @param point                        The coordinate from where to begin.
     * @param previousBestValuedCoordinate The previous best-valued coordinate.
     * @param numberOfVariables            The number of variables.
     * @param objectiveFunction                    The class in which the objective function is defined.
     * @return The objective function value at a nearby.
     */
    private double trialPhase(double[] stepLengthsForIterations,
                              double[] point,
                              double previousBestValuedCoordinate,
                              int numberOfVariables,
                              ObjectiveFunction objectiveFunction) {

        double minumumValueOfFunction;
        double[] currentSearchPoint = new double[MAXIMUM_NUMBER_OF_VARIABLES];
        double currentFunctionValue;

        int iterationNumber;

        minumumValueOfFunction = previousBestValuedCoordinate;

        loadCurrentSearchPoint(point, numberOfVariables, currentSearchPoint);

        for (iterationNumber = 0; iterationNumber < numberOfVariables; iterationNumber++) {
            currentSearchPoint[iterationNumber] = point[iterationNumber] + stepLengthsForIterations[iterationNumber];
//            currentFunctionValue = objectiveFunction.findValueForArguments(currentSearchPoint);
            currentFunctionValue=setNewParameterValuesAndFindMaterialFunctionValue(objectiveFunction,
                    currentSearchPoint);
            setNumberOfFunctionEvaluations(getNumberOfFunctionEvaluations() + 1);

            if (currentFunctionValue < minumumValueOfFunction) {
                minumumValueOfFunction = currentFunctionValue;
            } else {
                stepLengthsForIterations[iterationNumber] = 0.0 - stepLengthsForIterations[iterationNumber];
                currentSearchPoint[iterationNumber] = point[iterationNumber] + stepLengthsForIterations[iterationNumber];
//                currentFunctionValue = objectiveFunction.findValueForArguments(currentSearchPoint);
                currentFunctionValue=setNewParameterValuesAndFindMaterialFunctionValue(objectiveFunction,
                        currentSearchPoint);
                setNumberOfFunctionEvaluations(getNumberOfFunctionEvaluations() + 1);

                minumumValueOfFunction = checkIfMinimumFound(point, minumumValueOfFunction, currentSearchPoint, currentFunctionValue, iterationNumber);
            }
        }

        loadCurrentSearchPoint(currentSearchPoint, numberOfVariables, point);

        return minumumValueOfFunction;
    }

    private double checkIfMinimumFound(double[] point, double minumumValueOfFunction, double[] currentSearchPoint, double currentFunctionValue, int iterationNumber) {
        if (currentFunctionValue < minumumValueOfFunction) {
            minumumValueOfFunction = currentFunctionValue;
        } else {
            currentSearchPoint[iterationNumber] = point[iterationNumber];
        }
        return minumumValueOfFunction;
    }

    private void loadCurrentSearchPoint(double[] point, int numberOfVariables, double[] currentSearchPoint) {
        int iterationNumber;
        for (iterationNumber = 0; iterationNumber < numberOfVariables; iterationNumber++) {
            currentSearchPoint[iterationNumber] = point[iterationNumber];
        }
    }

    public void findMinimum(ObjectiveFunction objectiveFunction) {
        int defaultNumberOfVariables = 2;
        double[] defaultStartingCoordinates = new double[defaultNumberOfVariables];
        double[] defaultEndingCoordinates = new double[defaultNumberOfVariables];
        double defaultStepsizeGeometricShrink = 0.5;
        double defaultEpsilon = ENDING_VALUE_OF_STEPSIZE;
        findMinimum(defaultNumberOfVariables, defaultStartingCoordinates, defaultEndingCoordinates,
                defaultStepsizeGeometricShrink, defaultEpsilon, MAXIMUM_NUMBER_OF_VARIABLES, objectiveFunction);
    }

    public void findMinimum(int numberOfVariables,
                            double[] startingPointCoordinates,
                            double[] endingPointCoordinates,
                            double rhoStepsizeGeometricShrink,
                            double epsilonEndingValueOfStepsize,
                            int maximumNumberOfIterations,
                            ObjectiveFunction objectiveFunction) {

        double[] previousFunctionArguments = new double[MAXIMUM_NUMBER_OF_VARIABLES];
        double[] newFunctionArguments = new double[MAXIMUM_NUMBER_OF_VARIABLES];
        double[] stepSizeLenghts = new double[MAXIMUM_NUMBER_OF_VARIABLES];

        loadStepsSizeLenghtsForCoordinates(numberOfVariables, startingPointCoordinates, rhoStepsizeGeometricShrink, previousFunctionArguments, newFunctionArguments, stepSizeLenghts);

        double previousFunctionValue = setNewParameterValuesAndFindMaterialFunctionValue(objectiveFunction, newFunctionArguments);
        setNumberOfFunctionEvaluations(getNumberOfFunctionEvaluations() + 1);

        double newFunctionValue = previousFunctionValue;
        double stepLength = rhoStepsizeGeometricShrink;
        while ((getNumberOfIterations() < maximumNumberOfIterations) && (stepLength > epsilonEndingValueOfStepsize)) {
            setNumberOfIterations(getNumberOfIterations() + 1);

            logIterationInformations(numberOfVariables, previousFunctionArguments, previousFunctionValue);

            findBestNewPoint(numberOfVariables, newFunctionArguments, previousFunctionArguments);

            newFunctionValue = trialPhase(stepSizeLenghts, newFunctionArguments, previousFunctionValue, numberOfVariables,
                    objectiveFunction);


            int keep = 1;
            NewDirectionPursuer newDirectionPursuer = new NewDirectionPursuer(numberOfVariables, objectiveFunction,
                    newFunctionArguments, previousFunctionArguments, stepSizeLenghts, previousFunctionValue, newFunctionValue,
                    keep).invoke();
            newFunctionValue = newDirectionPursuer.getNewFunctionValue();
            previousFunctionValue = newDirectionPursuer.getPreviousFunctionValue();

            stepLength = findNewStepLenght(numberOfVariables, rhoStepsizeGeometricShrink, epsilonEndingValueOfStepsize, stepSizeLenghts,
                    previousFunctionValue, newFunctionValue, stepLength);
        }

        findBestNewPoint(numberOfVariables, endingPointCoordinates, previousFunctionArguments);
        setResultPointCoordinates(endingPointCoordinates);

    }

    @Override
    public double setNewParameterValuesAndFindMaterialFunctionValue(ObjectiveFunction objectiveFunction, double[]
            newFunctionArguments) {
        objectiveFunction.getMaterialFunction().setNewOptimizedParameterValues(newFunctionArguments);
        return objectiveFunction.findValueForArguments(newFunctionArguments);
    }

    private void loadStepsSizeLenghtsForCoordinates(int numberOfVariables, double[] startingPointCoordinates, double rhoStepsizeGeometricShrink, double[] previousFunctionArguments, double[] newFunctionArguments, double[] stepSizeLenghts) {
        for (int currentCoordinateNumber = 0; currentCoordinateNumber < numberOfVariables; currentCoordinateNumber++) {
            previousFunctionArguments[currentCoordinateNumber] = startingPointCoordinates[currentCoordinateNumber];
            newFunctionArguments[currentCoordinateNumber] = previousFunctionArguments[currentCoordinateNumber];

            loadStepsizeLenghtsForAllSteps(startingPointCoordinates, rhoStepsizeGeometricShrink, stepSizeLenghts,
                    currentCoordinateNumber);
        }
    }

    private void loadStepsizeLenghtsForAllSteps(double[] startingPointCoordinates, double rhoStepsizeGeometricShrink, double[] stepSizeLenghts, int currentCoordinateNumber) {
        stepSizeLenghts[currentCoordinateNumber] = Math.abs(startingPointCoordinates[currentCoordinateNumber] *
                rhoStepsizeGeometricShrink);

        ifStepsizeLenghtIsZeroSetItToValueOfGeometricShrink(rhoStepsizeGeometricShrink, stepSizeLenghts, currentCoordinateNumber);
    }

    private void ifStepsizeLenghtIsZeroSetItToValueOfGeometricShrink(double rhoStepsizeGeometricShrink, double[] stepSizeLenghts, int currentCoordinateNumber) {
        if (stepSizeLenghts[currentCoordinateNumber] == 0.0) {
            stepSizeLenghts[currentCoordinateNumber] = rhoStepsizeGeometricShrink;
        }
    }

    private double findNewStepLenght(int numberOfVariables, double rhoStepsizeGeometricShrink, double epsilonEndingValueOfStepsize,
                                     double[] delta, double previousFunctionValue, double newFunctionValue, double stepLength) {
        if ((stepLength >= epsilonEndingValueOfStepsize) && (newFunctionValue >= previousFunctionValue)) {
            stepLength = stepLength * rhoStepsizeGeometricShrink;

            for (int currentVariableIndex = 0; currentVariableIndex < numberOfVariables; currentVariableIndex++) {
                delta[currentVariableIndex] *= rhoStepsizeGeometricShrink;
            }
        }
        return stepLength;
    }

    private void logIterationInformations(int numberOfVariables, double[] previousFunctionArguments, double
            previousFunctionValue) {
        System.out.printf(
                "\nAfter %5d funevals, f(currentSearchPoints) =  %.4e at\n", getNumberOfFunctionEvaluations(), previousFunctionValue
        );

        for (int currentNumberOfVariable = 0; currentNumberOfVariable < numberOfVariables; currentNumberOfVariable++) {
            System.out.printf("   currentSearchPoints[%2d] = %.4e\n", currentNumberOfVariable,
                    previousFunctionArguments[currentNumberOfVariable]);
        }

        System.out.println("f(currentSearchPoints) =  "+ previousFunctionValue);
    }

    private void findBestNewPoint(int numberOfVariables, double[] newFunctionArguments, double[] previousFunctionArguments) {
        for (int newPointCoordinates = 0; newPointCoordinates < numberOfVariables; newPointCoordinates++) {
            newFunctionArguments[newPointCoordinates] = previousFunctionArguments[newPointCoordinates];
        }
    }

    @Override
    public double[] getResultPointCoordinates() {
        return resultPointCoordinates;
    }

    @Override
    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public void setResultPointCoordinates(double[] resultPointCoordinates) {
        this.resultPointCoordinates = resultPointCoordinates;
    }

    public int getNumberOfFunctionEvaluations() {
        return numberOfFunctionEvaluations;
    }

    public void setNumberOfFunctionEvaluations(int numberOfFunctionEvaluations) {
        this.numberOfFunctionEvaluations = numberOfFunctionEvaluations;
    }

    private class NewDirectionPursuer {
        private int numberOfVariables;
        private ObjectiveFunction objectiveFunction;
        private double[] newFunctionArguments;
        private double[] previousFunctionArguments;
        private double[] delta;
        private double previousFunctionValue;
        private double newFunctionValue;
        private int keep;

        public NewDirectionPursuer(int numberOfVariables, ObjectiveFunction objectiveFunction, double[]
                newFunctionArguments, double[] previousFunctionArguments, double[] delta, double
                                           previousFunctionValue, double newFunctionValue, int keep) {
            this.numberOfVariables = numberOfVariables;
            this.objectiveFunction = objectiveFunction;
            this.newFunctionArguments = newFunctionArguments;
            this.previousFunctionArguments = previousFunctionArguments;
            this.delta = delta;
            this.previousFunctionValue = previousFunctionValue;
            this.newFunctionValue = newFunctionValue;
            this.keep = keep;
        }

        public double getPreviousFunctionValue() {
            return previousFunctionValue;
        }

        public double getNewFunctionValue() {
            return newFunctionValue;
        }

        public NewDirectionPursuer invoke() {
            while ((newFunctionValue < previousFunctionValue) && (keep == 1)) {

                for (int variableNumber = 0; variableNumber < numberOfVariables; variableNumber++) {
                    arrangeSignOfDelta(variableNumber);

                    purseFurtherInCurrentDirection(variableNumber);
                }

                previousFunctionValue = newFunctionValue;

                newFunctionValue = trialPhase(delta, newFunctionArguments, previousFunctionValue, numberOfVariables,
                        objectiveFunction);

                if (furtherOptimisticStepFailed()) {
                    break;
                }

            }
            return this;
        }

        private void arrangeSignOfDelta(int i) {
            if (newFunctionArguments[i] <= previousFunctionArguments[i]) {
                delta[i] = 0.0 - Math.abs(delta[i]);
            } else {
                delta[i] = Math.abs(delta[i]);
            }
        }

        private void purseFurtherInCurrentDirection(int i) {
            double tmp;
            tmp = previousFunctionArguments[i];
            previousFunctionArguments[i] = newFunctionArguments[i];
            newFunctionArguments[i] = newFunctionArguments[i] + newFunctionArguments[i] - tmp;
        }

        private boolean furtherOptimisticStepFailed() {
            return newFunctionValue >= previousFunctionValue;
        }
    }

    public void setCurrentSearchPoints(double[] currentSearchPoints) {

    //TODO - ŁW przydałoby się to zrobić
    }
}
