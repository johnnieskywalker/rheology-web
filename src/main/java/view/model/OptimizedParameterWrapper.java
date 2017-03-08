package view.model;

public class OptimizedParameterWrapper {
    String name;

    double initialValue;

    double resultValue;


    public OptimizedParameterWrapper(String name, double initialValue, double resultValue) {
        this.name = name;
        this.initialValue = initialValue;
        this.resultValue = resultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(double initialValue) {
        this.initialValue = initialValue;
    }

    public double getResultValue() {
        return resultValue;
    }

    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }
}
