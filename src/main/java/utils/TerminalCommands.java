package utils;

public enum TerminalCommands {
    GREET("greet"),
    DATE("date"),
    LAST_FILE("lastFile"),
    LIST_FILES("ls"),
    READ_FILE("read"),
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
