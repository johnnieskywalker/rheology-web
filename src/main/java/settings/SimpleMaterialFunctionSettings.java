package settings;

import utils.ConstantValues;

public class SimpleMaterialFunctionSettings extends MaterialFunctionSettings {
    double startingKValue = ConstantValues.STARTING_K_VALUE;
    double startingNValue = ConstantValues.STARTING_N_VALUE;

    public double getStartingKValue() {
        return startingKValue;
    }

    public void setStartingKValue(double startingKValue) {
        this.startingKValue = startingKValue;
    }

    public double getStartingNValue() {
        return startingNValue;
    }

    public void setStartingNValue(double startingNValue) {
        this.startingNValue = startingNValue;
    }
}
