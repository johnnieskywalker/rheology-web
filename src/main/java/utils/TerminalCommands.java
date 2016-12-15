package utils;

public enum TerminalCommands {
    GREET("greet"),
    DATE("date"),
    LIST_FILES("ls"),
    HELP("help");

    private final String text;

    private TerminalCommands(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
