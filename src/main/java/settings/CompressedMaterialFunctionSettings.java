package settings;

public class CompressedMaterialFunctionSettings extends MaterialFunctionSettings {

    double startingR0 = 1.0;
    double startingK0 = 4.0;
    double startingN = 1.0;
    double startingBeta = 1.0;
    double startingKs = 1.0;
    double startingBetas = 1.0;
    double startingM = 6.0;

    double processTemperature = 1200.0;
    double processStrainRate = 1.0;

    public double getStartingR0() {
        return startingR0;
    }

    public void setStartingR0(double startingR0) {
        this.startingR0 = startingR0;
    }

    public double getStartingK0() {
        return startingK0;
    }

    public void setStartingK0(double startingK0) {
        this.startingK0 = startingK0;
    }

    public double getStartingN() {
        return startingN;
    }

    public void setStartingN(double startingN) {
        this.startingN = startingN;
    }

    public double getStartingBeta() {
        return startingBeta;
    }

    public void setStartingBeta(double startingBeta) {
        this.startingBeta = startingBeta;
    }

    public double getStartingKs() {
        return startingKs;
    }

    public void setStartingKs(double startingKs) {
        this.startingKs = startingKs;
    }

    public double getStartingBetas() {
        return startingBetas;
    }

    public void setStartingBetas(double startingBetas) {
        this.startingBetas = startingBetas;
    }

    public double getStartingM() {
        return startingM;
    }

    public void setStartingM(double startingM) {
        this.startingM = startingM;
    }

    public double getProcessTemperature() {
        return processTemperature;
    }

    public void setProcessTemperature(double processTemperature) {
        this.processTemperature = processTemperature;
    }

    public double getProcessStrainRate() {
        return processStrainRate;
    }

    public void setProcessStrainRate(double processStrainRate) {
        this.processStrainRate = processStrainRate;
    }
}
