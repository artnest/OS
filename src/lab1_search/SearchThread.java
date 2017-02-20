package lab1_search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchThread extends Thread{
    SearchInfo searchInfo;

    private SearchThread() {}

    public SearchThread(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    public SearchThread(String name, SearchInfo searchInfo) {
        super(name);
        this.searchInfo = searchInfo;
    }

    @Override
    public void run() {
        try {
            list(searchInfo.path, searchInfo.template);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private void list(String path, String template) throws FileNotFoundException {
        File f = new File(path);
        String[] dirList = f.list();
        List<String> files = new ArrayList<>();

        if (searchInfo.isSubdirectory) {
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

        if (searchInfo.isSubstring) {
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
                if (tempString.contains(searchInfo.substring)) {
                    files.add(tempFile.getAbsolutePath());
                }
            }
        }

        for (String s : files) {
            System.out.println(this.getName() + "   " + s);
        }
        // TODO Вывод в текстовое поле найденных файлов
        // TODO pass the necessary JTextArea, assign to field

    }

    private static String getRegex(String template) {
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
    }
}
