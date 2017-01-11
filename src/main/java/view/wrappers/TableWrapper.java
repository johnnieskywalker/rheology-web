package view.wrappers;

public class TableWrapper {

    double deformation;

    double experimentalStress;

    double calculatedStress;

    double meanSquaredError;

    public TableWrapper(double deformation, double experimentalStress) {
        this.deformation = deformation;
        this.experimentalStress = experimentalStress;
    }

    public double getDeformation() {
        return deformation;
    }

    public void setDeformation(double deformation) {
        this.deformation = deformation;
    }

    public double getExperimentalStress() {
        return experimentalStress;
    }

    public void setExperimentalStress(double experimentalStress) {
        this.experimentalStress = experimentalStress;
    }

    public double getCalculatedStress() {
        return calculatedStress;
    }

    public void setCalculatedStress(double calculatedStress) {
        this.calculatedStress = calculatedStress;
    }

    public double getMeanSquaredError() {
        return meanSquaredError;
    }

    public void setMeanSquaredError(double meanSquaredError) {
        this.meanSquaredError = meanSquaredError;
    }
}
