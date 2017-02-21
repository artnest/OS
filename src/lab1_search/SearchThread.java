package lab1_search;

public class SearchThread extends Thread {
    private SearchAttributes searchAttributes;

    public SearchThread() {
    }

    /*public SearchThread(SearchAttributes searchAttributes) {
        this.searchAttributes = searchAttributes;
    }*/

    /*public SearchThread(String name, SearchAttributes searchAttributes) {
        super(name);
        this.searchAttributes = searchAttributes;
    }*/

    public SearchAttributes getSearchAttributes() {
        return searchAttributes; // TODO remove?
    }

    public void setSearchAttributes(SearchAttributes searchAttributes) {
        this.searchAttributes = searchAttributes;
    }

    @Override
    public void run() {
/*
        try {
            list(searchAttributes.getPath(), searchAttributes.getTemplate());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
*/

    }

    /*private void list(String path, String template) throws FileNotFoundException {
        File f = new File(path);
        String[] dirList = f.list();
        List<String> files = new ArrayList<>();

        if (searchAttributes.isSubdirectory()) {
            try {
                for (String fileName : dirList) {
                    if (fileName.charAt(0) == '.') {
                        continue;
                    }
                    File tempFile = new File( path + "/" + fileName);
                    if (!tempFile.isFile()) {
                        list(path + "/" + fileName, template);
                    }
                }
            } catch (NullPointerException e) {
                System.err.println("Permission denied"); // TODO Print to List
            }
        }

        for (String s : dirList) {
            Matcher matcher = Pattern.compile(getRegex(template)).matcher(s);
            while (matcher.find()) {
                files.add(path + "/" + matcher.group());
            }
        }

        if (searchAttributes.isSubstring()) {
            String[] names = files.stream().toArray(String[]::new);
            files.clear();
            for (String fileName : names) {
                if (fileName.charAt(0) == '.') {
                    continue;
                }
                File tempFile = new File(fileName);
                String tempString = "";
                Scanner in = new Scanner(tempFile);
                while(in.hasNext())
                    tempString += in.nextLine() + "\r\n";
                in.close();
                if (tempString.contains(searchAttributes.getSubstring())) {
                    files.add(tempFile.getAbsolutePath());
                }
            }
        }

        for (String s : files) {
            System.out.println(this.getName() + "   " + s);
        }
        // TODO Вывод в текстовое поле найденных файлов
        // TODO pass the necessary JTextArea, assign to field
    }*/

    /*private static String getRegex(String template) {
        StringBuilder regex = new StringBuilder("^");
        for (char c : template.toCharArray()) {
            switch (c)
            {
                case '.':
                    regex.append("\\.");
                    break;
                case '?':
                    regex.append(".");
                    break;
                case '*':
                    regex.append(".*");
                    break;
                default:
                    regex.append(c);
            }
        }
        return regex.toString();
    }*/
}
