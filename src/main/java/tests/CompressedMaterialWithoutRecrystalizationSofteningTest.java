package tests;

import optimization.nonlinear.unconstrained.core.materialFunctions.CompressedMaterialWithoutRecrystalizationSoftening;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class CompressedMaterialWithoutRecrystalizationSofteningTest {

    CompressedMaterialWithoutRecrystalizationSoftening compressedMaterialWithoutRecrystalizationSoftening;

    @Before
    public void setUp() {
        double startingR0 = 0.281819330397332;
        double startingK0 = 4.28531186111313;
        double startingN = 0.307236289544873;
        double startingBeta = 2.59731511945659;
        double startingKs = -2.05711719392693;
        double startingBetas = 51.8382906608294;
        double startingM = 5.80888097011669;

        double processTemperature = 1200.0;
        double processStrainRate=1.0;

        compressedMaterialWithoutRecrystalizationSoftening = new  CompressedMaterialWithoutRecrystalizationSoftening();

        compressedMaterialWithoutRecrystalizationSoftening.setR0(startingR0);
        compressedMaterialWithoutRecrystalizationSoftening.setK0(startingK0);
        compressedMaterialWithoutRecrystalizationSoftening.setN(startingN);
        compressedMaterialWithoutRecrystalizationSoftening.setBeta(startingBeta);
        compressedMaterialWithoutRecrystalizationSoftening.setKs(startingKs);
        compressedMaterialWithoutRecrystalizationSoftening.setBetas(startingBetas);
        compressedMaterialWithoutRecrystalizationSoftening.setM(startingM);

        compressedMaterialWithoutRecrystalizationSoftening.setProcessTemperature(processTemperature);
        compressedMaterialWithoutRecrystalizationSoftening.setProcessStrainRate(processStrainRate);
    }

    @Test
    public void stressInPointShouldBeAroundExpected(){
        double deformation = 0.000903;

        System.out.println(compressedMaterialWithoutRecrystalizationSoftening.calculateW(deformation));

        double stressInPoint = compressedMaterialWithoutRecrystalizationSoftening.calculateMaterialStressInPoint
                (deformation);

        System.out.println("calculated stress for deformation(1) = "+stressInPoint);

        double allowedDifferenceBetweenResultFromExcel=0.000000001;
        assertEquals(20.95767131,stressInPoint,allowedDifferenceBetweenResultFromExcel);
    }

}
