package calculations;

import optimization.nonlinear.unconstrained.core.SumRootMeanSquaredErrorsObjectiveFunction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import view.wrappers.TableRowWrapper;

import java.util.ArrayList;
import java.util.List;

public class OptimizedValuesToTableWrappersConverterTest {

    OptimizedValuesToTableWrappersConverter optimizedValuesToTableWrappersConverter;

    SumRootMeanSquaredErrorsObjectiveFunction sumMeanSquaredErrorsObjectiveFunction;
    List<TableRowWrapper> tableRowWrappers = new ArrayList<>();

    double testPaarameterK = 3.0;
    double testPaarameterN = 1.0;

    double testDeformation = 2.0;
    double testExperimentalStress = 5.0;

    //TODO - ŁW z jakiegos powodu w testach leci null ale webapp dziala
    @Before
    public void setUp() {
        sumMeanSquaredErrorsObjectiveFunction = new SumRootMeanSquaredErrorsObjectiveFunction();

        optimizedValuesToTableWrappersConverter = new OptimizedValuesToTableWrappersConverter();
        optimizedValuesToTableWrappersConverter.setObjectiveFunction(sumMeanSquaredErrorsObjectiveFunction);
        optimizedValuesToTableWrappersConverter.setOptimizedParameterK(testPaarameterK);
        optimizedValuesToTableWrappersConverter.setOptimizedParameterN(testPaarameterN);

        tableRowWrappers.add(new TableRowWrapper(testDeformation, testExperimentalStress));
        optimizedValuesToTableWrappersConverter.setTableRowWrappers(tableRowWrappers);

        optimizedValuesToTableWrappersConverter.fillTableWrappersWithCalculatedValues();
    }

    @Test
    public void calculatedStressShouldBeAsExpected() {
        Double expectedCalculatedStress = 6.0;
        Double calculatedStressInRow = optimizedValuesToTableWrappersConverter
                .getTableRowWrappers()
                .get(0).getCalculatedStress();
        Assert.assertEquals(expectedCalculatedStress, calculatedStressInRow);
    }

    @Test
    public void meanSquaredErrorShouldBeAsExpected() {
        Double expectedMeanSquaredError = 1.0;
        Double meanSquaredError = optimizedValuesToTableWrappersConverter
                .getTableRowWrappers()
                .get(0).getMeanSquaredError();

        Assert.assertEquals(expectedMeanSquaredError, meanSquaredError);
    }

}
