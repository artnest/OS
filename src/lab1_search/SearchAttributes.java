package lab1_search;

class SearchAttributes {
    private String path;
    private String template;
    private String substring;

    private boolean isSubdirectory = false;
    private boolean isTemplate = false;
    private boolean isSubstring = false;

    SearchAttributes(String path, String template, String substring,
                     boolean isSubdirectory, boolean isTemplate, boolean isSubstring) {
        this.path = path;
        this.template = template;
        this.substring = substring;
        this.isSubdirectory = isSubdirectory;
        this.isTemplate = isTemplate;
        this.isSubstring = isSubstring;
    }

    public String getPath() {
        return path;
    }

    public String getTemplate() {
        return template;
    }

    public String getSubstring() {
        return substring;
    }

    public boolean isSubdirectory() {
        return isSubdirectory;
    }

    public boolean isTemplate() {
        return isTemplate;
    }

    public boolean isSubstring() {
        return isSubstring;
    }
}
