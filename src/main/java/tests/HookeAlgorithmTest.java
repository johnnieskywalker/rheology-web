package tests;

import optimization.nonlinear.unconstrained.core.ApproximationFromFileTask;
import optimization.nonlinear.unconstrained.core.HookeAlgorithm;
import optimization.nonlinear.unconstrained.core.materialFunctions.CompressedMaterialWithoutRecrystalizationSoftening;
import optimization.nonlinear.unconstrained.core.materialFunctions.SimpleMaterialFunction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HookeAlgorithmTest {

    ApproximationFromFileTask approximationFromFileTask;

    @Before
    public void setUp() {
        approximationFromFileTask = new ApproximationFromFileTask();
    }

    @Test
    public void shouldFindMinimumOfSimpleFunctionCloseToExcelSolverSolution() {
        double rho = approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction().STRPSIZE_GEOMETRIC_SHRINK_RHO;
        double epsilon = HookeAlgorithm.ENDING_VALUE_OF_STEPSIZE;
        int numberOfVariables = 2;

        double[] startPoint = new double[numberOfVariables];
        startPoint[HookeAlgorithm.INDEX_ZERO] = 0.1;
        startPoint[HookeAlgorithm.INDEX_ONE] = 0.1;

        loadHypotheticalData(approximationFromFileTask);
        double parameterK = startPoint[0];
        double parameterN = startPoint[1];
        SimpleMaterialFunction simpleMaterialFunction = new SimpleMaterialFunction();
        simpleMaterialFunction.setParameterK(parameterK);
        simpleMaterialFunction.setParameterN(parameterN);
        approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction().setMaterialFunction(simpleMaterialFunction);

        HookeAlgorithm hookeAlgorithm = new HookeAlgorithm();
        double[] resultPoints = new double[numberOfVariables];
        hookeAlgorithm.findMinimum(
                numberOfVariables, startPoint, resultPoints, rho, epsilon, HookeAlgorithm.MAXIMUM_NUMBER_OF_ITERATIONS,
                approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction());
        int numberOfIterations = hookeAlgorithm.getNumberOfIterations();

        printResults(numberOfVariables, numberOfIterations, resultPoints);

        double allowedDifferenceBetweenResultFromExcelForK = 1.0;
        assertEquals(144.688103141454, resultPoints[0], allowedDifferenceBetweenResultFromExcelForK);
        double allowedDifferenceBetweenResultFromExcelForN = 0.003;
        assertEquals(0.237095895731918, resultPoints[1], allowedDifferenceBetweenResultFromExcelForN);
    }

    //Material Function
    //Testing of the inverse software for identification of
    //rheological models of materials subjected to plastic deformation
    @Test
    public void shouldApproximateMinimumCompressedMaterialWithoutRecrystalizationSoftening() {
        loadHypotheticalData(approximationFromFileTask);

        double rho = approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction().STRPSIZE_GEOMETRIC_SHRINK_RHO;
        double epsilon = HookeAlgorithm.ENDING_VALUE_OF_STEPSIZE;

        double startingR0 = 1.0;
        double startingK0 = 4.0;
        double startingN = 1.0;
        double startingBeta = 1.0;
        double startingKs = 1.0;
        double startingBetas = 1.0;
        double startingM = 6.0;

        double processTemperature = 1200.0;
        double processStrainRate = 1.0;

        CompressedMaterialWithoutRecrystalizationSoftening compressedMaterialWithoutRecrystalizationSoftening = new
                CompressedMaterialWithoutRecrystalizationSoftening();

        compressedMaterialWithoutRecrystalizationSoftening.setR0(startingR0);
        compressedMaterialWithoutRecrystalizationSoftening.setK0(startingK0);
        compressedMaterialWithoutRecrystalizationSoftening.setN(startingN);
        compressedMaterialWithoutRecrystalizationSoftening.setBeta(startingBeta);
        compressedMaterialWithoutRecrystalizationSoftening.setKs(startingKs);
        compressedMaterialWithoutRecrystalizationSoftening.setBetas(startingBetas);
        compressedMaterialWithoutRecrystalizationSoftening.setM(startingM);

        compressedMaterialWithoutRecrystalizationSoftening.setProcessTemperature(processTemperature);
        compressedMaterialWithoutRecrystalizationSoftening.setProcessStrainRate(processStrainRate);

        approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction()
                .setMaterialFunction(compressedMaterialWithoutRecrystalizationSoftening);

        double[] startPoint = {startingR0, startingK0, startingN, startingBeta, startingKs, startingBetas, startingM};

        int numberOfVariables = 7;
        double[] resultPoints = new double[numberOfVariables];
        HookeAlgorithm hookeAlgorithm = new HookeAlgorithm();
        hookeAlgorithm.findMinimum(
                numberOfVariables, startPoint, resultPoints, rho, epsilon, HookeAlgorithm.MAXIMUM_NUMBER_OF_ITERATIONS,
                approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction());

        printResults(numberOfVariables, hookeAlgorithm.getNumberOfIterations(), resultPoints);

        double expectedR0 = 0.281819330397332;
        double expectedK0 = 4.28531186111313;
        double expectedN = 0.307236289544873;
        double expectedBeta = 2.59731511945659;
        double expectedKs = -2.05711719392693;
        double expectedBetas = 51.8382906608294;
        double expectedM = 5.80888097011669;
        double expectedDelta = 60.0;
        assertArrayEquals(null,
                new double[]{expectedR0, expectedK0,expectedN,expectedBeta,expectedKs,expectedBetas,expectedM},
                resultPoints,
                expectedDelta);

    }

    public static void loadHypotheticalData(ApproximationFromFileTask approximationFromFileTask) {
        approximationFromFileTask.loadExperimentalDataFromFilesPaths
                ("optimizationTasksData/kperzyns_aproksymacja_data/taskData/experimentalStress",
                        "optimizationTasksData/kperzyns_aproksymacja_data/taskData/experimentalDeformation");
    }

//    Data from JMatPro
//    Stal 38MnSV4
//    Temp 800C
//    predkosc odkształcenia : 1
    @Test
    public void shouldApproximateMinimumCompressedMaterialWithoutRecrystalizationSofteningUsingFEMData() {
        loadFEMDataFor38MnSV4(approximationFromFileTask);

        double rho = approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction().STRPSIZE_GEOMETRIC_SHRINK_RHO;
        double epsilon = HookeAlgorithm.ENDING_VALUE_OF_STEPSIZE;

        double startingR0 = 1.0;
        double startingK0 = 4.0;
        double startingN = 1.0;
        double startingBeta = 1.0;
        double startingKs = 1.0;
        double startingBetas = 1.0;
        double startingM = 6.0;

        double processTemperature = 800.0;
        double processStrainRate = 1.0;

        CompressedMaterialWithoutRecrystalizationSoftening compressedMaterialWithoutRecrystalizationSoftening = new
                CompressedMaterialWithoutRecrystalizationSoftening();

        compressedMaterialWithoutRecrystalizationSoftening.setR0(startingR0);
        compressedMaterialWithoutRecrystalizationSoftening.setK0(startingK0);
        compressedMaterialWithoutRecrystalizationSoftening.setN(startingN);
        compressedMaterialWithoutRecrystalizationSoftening.setBeta(startingBeta);
        compressedMaterialWithoutRecrystalizationSoftening.setKs(startingKs);
        compressedMaterialWithoutRecrystalizationSoftening.setBetas(startingBetas);
        compressedMaterialWithoutRecrystalizationSoftening.setM(startingM);

        compressedMaterialWithoutRecrystalizationSoftening.setProcessTemperature(processTemperature);
        compressedMaterialWithoutRecrystalizationSoftening.setProcessStrainRate(processStrainRate);

        approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction()
                .setMaterialFunction(compressedMaterialWithoutRecrystalizationSoftening);

        double[] startPoint = {startingR0, startingK0, startingN, startingBeta, startingKs, startingBetas, startingM};

        int numberOfVariables = 7;
        double[] resultPoints = new double[numberOfVariables];
        HookeAlgorithm hookeAlgorithm = new HookeAlgorithm();
        hookeAlgorithm.findMinimum(
                numberOfVariables, startPoint, resultPoints, rho, epsilon, HookeAlgorithm.MAXIMUM_NUMBER_OF_ITERATIONS,
                approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction());

        printResults(numberOfVariables, hookeAlgorithm.getNumberOfIterations(), resultPoints);

        double expectedR0 = 2.40745727147078;
        double expectedK0 = 8.87236792938996;
        double expectedN = 0.126984343385936;
        double expectedBeta = 14.430115193442;
        double expectedKs = 6.38443096809023;
        double expectedBetas = -12.4244449354746;
        double expectedM = 5.78962279112878;
        double expectedDelta = 19.0;
        assertArrayEquals(null,
                new double[]{expectedR0, expectedK0,expectedN,expectedBeta,expectedKs,expectedBetas,expectedM},
                resultPoints,
                expectedDelta);

    }

    public static void loadFEMDataFor38MnSV4(ApproximationFromFileTask approximationFromFileTask) {
        approximationFromFileTask.loadExperimentalDataFromFilesPaths
                ("optimizationTasksData/JMatPro/microAlloy/naprezeniaWTemp800PredkoscOdkszt1.txt",
                        "optimizationTasksData/JMatPro/microAlloy/odksztalcenia.txt");
    }

    private static void printResults(int numberOfVariables, int numberOfIterations, double[] resultPoints) {
        System.out.println(
                "\nHOOKE USED " + numberOfIterations + " ITERATIONS, AND RETURNED"
        );

        for (int i = 0; i < numberOfVariables; i++) {
//            System.out.printf("x[%3d] = %15.7e \n", i, resultPoints[i]);
            System.out.println("x["+i+"] = "+ resultPoints[i]);

        }
    }

    //time test for lots of data
    //everything else is copied
    @Test
    public void testHowLongWillApproximate1000points() {
        double rho = approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction().STRPSIZE_GEOMETRIC_SHRINK_RHO;
        double epsilon = HookeAlgorithm.ENDING_VALUE_OF_STEPSIZE;
        int numberOfVariables = 2;

        double[] startPoint = new double[numberOfVariables];
        startPoint[HookeAlgorithm.INDEX_ZERO] = 0.1;
        startPoint[HookeAlgorithm.INDEX_ONE] = 0.1;

        loadFake1000PointsData(approximationFromFileTask);
        double parameterK = startPoint[0];
        double parameterN = startPoint[1];
        SimpleMaterialFunction simpleMaterialFunction = new SimpleMaterialFunction();
        simpleMaterialFunction.setParameterK(parameterK);
        simpleMaterialFunction.setParameterN(parameterN);
        approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction().setMaterialFunction(simpleMaterialFunction);

        HookeAlgorithm hookeAlgorithm = new HookeAlgorithm();
        double[] resultPoints = new double[numberOfVariables];
        hookeAlgorithm.findMinimum(
                numberOfVariables, startPoint, resultPoints, rho, epsilon, HookeAlgorithm.MAXIMUM_NUMBER_OF_ITERATIONS,
                approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction());
        int numberOfIterations = hookeAlgorithm.getNumberOfIterations();

        printResults(numberOfVariables, numberOfIterations, resultPoints);

    }

    @Test
    public void testHowLongWillApproximate65000points() {
        double rho = approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction().STRPSIZE_GEOMETRIC_SHRINK_RHO;
        double epsilon = HookeAlgorithm.ENDING_VALUE_OF_STEPSIZE;
        int numberOfVariables = 2;

        double[] startPoint = new double[numberOfVariables];
        startPoint[HookeAlgorithm.INDEX_ZERO] = 0.1;
        startPoint[HookeAlgorithm.INDEX_ONE] = 0.1;

        loadFake65000PointsData(approximationFromFileTask);
        double parameterK = startPoint[0];
        double parameterN = startPoint[1];
        SimpleMaterialFunction simpleMaterialFunction = new SimpleMaterialFunction();
        simpleMaterialFunction.setParameterK(parameterK);
        simpleMaterialFunction.setParameterN(parameterN);
        approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction().setMaterialFunction(simpleMaterialFunction);

        HookeAlgorithm hookeAlgorithm = new HookeAlgorithm();
        double[] resultPoints = new double[numberOfVariables];
        hookeAlgorithm.findMinimum(
                numberOfVariables, startPoint, resultPoints, rho, epsilon, HookeAlgorithm.MAXIMUM_NUMBER_OF_ITERATIONS,
                approximationFromFileTask.getSumMeanSquaredErrorsObjectiveFunction());
        int numberOfIterations = hookeAlgorithm.getNumberOfIterations();

        printResults(numberOfVariables, numberOfIterations, resultPoints);
    }

    public static void loadFake1000PointsData(ApproximationFromFileTask approximationFromFileTask) {
        approximationFromFileTask.loadExperimentalDataFromFilesPaths
                ("optimizationTasksData/fakeGeneratedData/1000points/fake1000stress.txt",
                        "optimizationTasksData/fakeGeneratedData/1000points/fake1000def.txt");
    }

    public static void loadFake65000PointsData(ApproximationFromFileTask approximationFromFileTask) {
        approximationFromFileTask.loadExperimentalDataFromFilesPaths
                ("optimizationTasksData/fakeGeneratedData/65000points/fake65000stress.txt",
                        "optimizationTasksData/fakeGeneratedData/65000points/fake65000def.txt");
    }

}
