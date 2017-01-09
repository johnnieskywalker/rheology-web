/*
 * nlp-unconstrained-core/hooke-jeeves/java/src/main/java/
 * optimization/nonlinear/unconstrained/core/Woods.java
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

/**
 * The <code>Woods</code> class is responsible for solving a nonlinear
 * optimization problem using the algorithm of Hooke and Jeeves.
 * <br />
 * <br />The objective function in this case
 * is the so-called &quot;Woods&quot; function.
 *
 * @author  Radislav (Radic) Golubtsov
 * @version 0.1
 * @see     Hooke
 * @since   findMinimum-jeeves 0.1
 */
public final class Woods implements ObjectiveFunction {
    /**
     * Constant. The stepsize geometric shrink.
     * <br />
     * <br />The Hooke &amp; Jeeves algorithm works reasonably well
     * on Rosenbrock's function, but can fare worse on some standard
     * test functions, depending on rho. Here is an example that works well
     * when rho = 0.5, but fares poorly with rho = 0.6, and better again
     * with rho = 0.8.
     */
    private static final double RHO_WOODS = 0.6;

    /** Helper constants. */
    private static final int    INDEX_TWO   =  2;
    private static final int    INDEX_THREE =  3;
    private static final int    ONE_HUNDRED =  100;
    private static final int    NINETY      =  90;
    private static final int    TEN         =  10;
    private static final double TEN_POINT   =  10.;
    private static final int    FOUR        =  4;
    private static final int    MINUS_THREE = -3;
    private static final int    MINUS_ONE   = -1;

    /**
     * The user-supplied objective function f(x,n).
     * <br />
     * <br />Woods &ndash; a la More, Garbow &amp; Hillstrom
     * (TOMS algorithm 566).
     *
     * @param x The point at which f(x) should be evaluated.
     *
     * @return The objective function value.
     */
    public double findValueForArguments(final double[] x) {
        double s1;
        double s2;
        double s3;
        double t1;
        double t2;
        double t3;
        double t4;
        double t5;

        s1 = x[Hooke.INDEX_ONE] - x[Hooke.INDEX_ZERO] * x[Hooke.INDEX_ZERO];
        s2 = 1                  - x[Hooke.INDEX_ZERO];
        s3 = x[Hooke.INDEX_ONE] - 1;

        t1 = x[INDEX_THREE]     - x[INDEX_TWO]        * x[INDEX_TWO];
        t2 = 1                  - x[INDEX_TWO];
        t3 = x[INDEX_THREE]     - 1;

        t4 = s3 + t3;
        t5 = s3 - t3;

        return (ONE_HUNDRED * (s1 * s1) + s2 * s2
                   + NINETY * (t1 * t1) + t2 * t2
                      + TEN * (t4 * t4) + t5 * t5 / TEN_POINT);
    }

    /**
     * Main program function.
     *
     * @param args The array of command-line arguments.
     */
    public static void main(final String[] args) {
        int nVars;
        int iterMax;
        int numberOfIterations;
        int i;

        double[] startPt = new double[Hooke.MAXIMUM_NUMBER_OF_VARIABLES];
        double rho;
        double epsilon;
        double[] endPt   = new double[Hooke.MAXIMUM_NUMBER_OF_VARIABLES];

        // Starting guess test problem "Woods".
        nVars                     = FOUR;
        startPt[Hooke.INDEX_ZERO] = MINUS_THREE;
        startPt[Hooke.INDEX_ONE]  = MINUS_ONE;
        startPt[INDEX_TWO]        = MINUS_THREE;
        startPt[INDEX_THREE]      = MINUS_ONE;
        iterMax                   = Hooke.MAXIMUM_NUMBER_OF_ITERATIONS;
        rho                       = RHO_WOODS;
        epsilon                   = Hooke.ENDING_VALUE_OF_STEPSIZE;

        Hooke hooke = new Hooke();
        hooke.findMinimum(
                nVars, startPt, endPt, rho, epsilon, iterMax, new Woods()
        );
        numberOfIterations = hooke.getNumberOfIterations();

        System.out.println(
            "\n\n\nHOOKE USED " + numberOfIterations + " ITERATIONS, AND RETURNED"
        );

        for (i = 0; i < nVars; i++) {
            System.out.printf("x[%3d] = %15.7e \n", i, endPt[i]);
        }

        System.out.println("True answer: f(1, 1, 1, 1) = 0.");
    }
}