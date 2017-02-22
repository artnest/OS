package lab1_search;

class SearchAttributes {
    private String path;
    private String pattern;
    private String substring;

    private boolean isSubdirectory = false;
    private boolean isPattern = false;
    private boolean isSubstring = false;

    SearchAttributes(String path, String pattern, String substring,
                     boolean isSubdirectory, boolean isPattern, boolean isSubstring) {
        this.path = path;
        this.pattern = pattern;
        this.substring = substring;
        this.isSubdirectory = isSubdirectory;
        this.isPattern = isPattern;
        this.isSubstring = isSubstring;
    }

    String getPath() {
        return path;
    }

    String getPattern() {
        return pattern;
    }

    String getSubstring() {
        return substring;
    }

    boolean isSubdirectory() {
        return isSubdirectory;
    }

    boolean isPattern() {
        return isPattern;
    }

    boolean isSubstring() {
        return isSubstring;
    }
}
