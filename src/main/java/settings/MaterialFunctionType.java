package settings;

public enum MaterialFunctionType {
    SIMPLE("σ=k*ε(i)^n"),
    COMPRESSED("σ=sqrt(3)* [W*K0*ε^n*exp(β/T)+(1-W)*Ks*exp(βs/T)]*(sqrt(3)*predkoscOdkszt)^m");

    private final String methodName;

    private MaterialFunctionType(final String text) {
        this.methodName = text;
    }

    @Override
    public String toString() {
        return methodName;
    }
}
