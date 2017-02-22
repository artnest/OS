package lab1_search;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class SearchThread extends Thread {
    private SearchAttributes searchAttributes;
    private JTextArea textArea;

    SearchThread(JTextArea textArea) {
        this.textArea = textArea;
    }

    SearchAttributes getSearchAttributes() {
        return searchAttributes;
    }

    void setSearchAttributes(SearchAttributes searchAttributes) {
        this.searchAttributes = searchAttributes;
    }

    @Override
    public void run() {
        if (!searchAttributes.isSubdirectory() &&
                !searchAttributes.isPattern() &&
                !searchAttributes.isSubstring()) {
            try {
/*
                Files.list(Paths.get(searchAttributes.getPath()))
                        .filter(Files::isRegularFile)
                        .forEach(path -> textArea.append(path + "\n"));
*/
                Files.newDirectoryStream(Paths.get(searchAttributes.getPath()),
                        path -> path.toFile().isFile())
                        .forEach(path -> textArea.append(path + "\n"));
            } catch (IOException e) {
                textArea.append(e.getCause() + "\n"); // TODO print to JTextPane
            }
        } else if (searchAttributes.isSubdirectory() &&
                !searchAttributes.isPattern() &&
                !searchAttributes.isSubstring()) {
            try {
                listFiles(searchAttributes.getPath());
            } catch (IOException e) {
                textArea.append(e.getCause() + "\n");
            }

        } else if (searchAttributes.isSubdirectory() &&
                searchAttributes.isPattern() &&
                !searchAttributes.isSubstring()) {
            try {
                Files.walkFileTree(Paths.get(searchAttributes.getPath()),
                        new FileFinder(searchAttributes.getPattern(), textArea));
            } catch (IOException e) {
                textArea.append(e.getCause() + "\n");
            }
        } else if (searchAttributes.isSubdirectory() &&
                !searchAttributes.isPattern() &&
                searchAttributes.isSubstring()) {
            try {
                String searchString = searchAttributes.getSubstring();
                Files.newDirectoryStream(Paths.get(searchAttributes.getPath()),
                        path -> path.toFile().isFile() && path.toString().endsWith(".txt"))
                        .forEach(path -> {
                            try {
                                Files.lines(path,
                                            Charset.defaultCharset())
                                        .forEach(line -> {
                                            if (line.contains(searchString)) {
                                                textArea.append(path + "\n");
                                            }
                                        });
                            } catch (IOException e) {
                                textArea.append(e.getCause() + "\n");
                            }
                        });
            } catch (IOException e) {
                textArea.append(e.getCause() + "\n");
            }
        } else if (searchAttributes.isSubdirectory() &&
                searchAttributes.isPattern() &&
                searchAttributes.isSubstring()) {
            try {
                Files.walkFileTree(Paths.get(searchAttributes.getPath()),
                        new FileFinder(searchAttributes.getPattern(), searchAttributes.getSubstring(), textArea));
            } catch (IOException e) {
                textArea.append(e.getCause() + "\n");
            }
        }

        // TODO add 4 more variants of settings combinations
    }

    private void listFiles(String path) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path))) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    listFiles(entry.toString());
                }
                textArea.append(entry.toString() + "\n");
            }
        }
    }
}
