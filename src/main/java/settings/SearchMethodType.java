package settings;

public enum SearchMethodType {
    HOOKE("Hooke-Jeeves"),
    POWELL("Powell");

    private final String methodName;

    private SearchMethodType(final String text) {
        this.methodName = text;
    }

    @Override
    public String toString() {
        return methodName;
    }
}
