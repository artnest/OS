package lab1_search;

class SearchInfo {
    public String path;
    public String template;
    public String substring;

    public boolean isSubdirectory = false;
    public boolean isTemplate = false;
    public boolean isSubstring = false;

    SearchInfo(String path, String template, String substring,
               boolean isSubdirectory, boolean isTemplate, boolean isSubstring) {
        this.path = path;
        this.template = template;
        this.substring = substring;
        this.isSubdirectory = isSubdirectory;
        this.isTemplate = isTemplate;
        this.isSubstring = isSubstring;
    }
}
