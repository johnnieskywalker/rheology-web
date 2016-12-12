package utils;

public enum SystemPaths {
    TOMCAT_UPLOAD_FOLDER("D:\\Tomcat\\TomcatUploads");

    private final String text;

    private SystemPaths(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
