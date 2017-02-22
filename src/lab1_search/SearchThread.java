package lab1_search;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

class SearchThread extends Thread {
    private SearchAttributes searchAttributes;
    private JTextArea textArea;
    private List<String> filePaths = new LinkedList<>();

    private final Object lock = new Object();

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
        boolean searchInSubdirectories = searchAttributes.isSubdirectory();
        boolean searchByPattern = searchAttributes.isPattern();
        boolean searchForSubstring = searchAttributes.isSubstring();

        synchronized (lock) {
            if (!searchInSubdirectories &&
                    !searchByPattern &&
                    !searchForSubstring) {
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
                    textArea.append(e.getCause() + "\n");
                }
            } else if (searchInSubdirectories &&
                    !searchByPattern &&
                    !searchForSubstring) {
                try {
                    listFiles(Paths.get(searchAttributes.getPath()));
                } catch (IOException e) {
                    textArea.append(e.getCause() + "\n");
                }

            } else if (searchInSubdirectories &&
                    searchByPattern &&
                    !searchForSubstring) {
                try {
                    Files.walkFileTree(Paths.get(searchAttributes.getPath()),
                            new FileFinder(searchAttributes.getPattern(), textArea));
                } catch (IOException e) {
                    textArea.append(e.getCause() + "\n");
                }
            } else if (searchInSubdirectories &&
                    !searchByPattern &&
                    searchForSubstring) {
                try {
                    listFiles(Paths.get(searchAttributes.getPath()), searchAttributes.getSubstring());
                } catch (IOException e) {
                    textArea.append(e.getCause() + "\n");
                }
            } else if (searchInSubdirectories &&
                    searchByPattern &&
                    searchForSubstring) {
                try {
                    Files.walkFileTree(Paths.get(searchAttributes.getPath()),
                            new FileFinder(searchAttributes.getPattern(), searchAttributes.getSubstring(), textArea));
                } catch (IOException e) {
                    textArea.append(e.getCause() + "\n");
                }
            } else if (!searchInSubdirectories &&
                    searchByPattern &&
                    !searchForSubstring) {
                try {
                    Files.walkFileTree(Paths.get(searchAttributes.getPath()),
                            new FileFinder(searchAttributes.getPattern(),
                                    searchInSubdirectories, Paths.get(searchAttributes.getPath()), textArea));
                } catch (IOException e) {
                    textArea.append(e.getCause() + "\n");
                }
            } else if (!searchInSubdirectories &&
                    searchByPattern &&
                    searchForSubstring) {
                try {
                    Files.walkFileTree(Paths.get(searchAttributes.getPath()),
                            new FileFinder(searchAttributes.getPattern(),
                                    searchAttributes.getSubstring(),
                                    searchInSubdirectories, Paths.get(searchAttributes.getPath()), textArea));
                } catch (IOException e) {
                    textArea.append(e.getCause() + "\n");
                }
            } else if (!searchInSubdirectories &&
                    !searchByPattern &&
                    searchForSubstring) {
                try {
                    String searchString = searchAttributes.getSubstring();
                    Files.newDirectoryStream(Paths.get(searchAttributes.getPath()),
                            path -> path.toFile().isFile() && path.toString().endsWith(".txt"))
                            .forEach(path -> {
                                try {
                                    Files.lines(path,
                                            Charset.defaultCharset())
                                            .forEach(line -> {
                                                if (line.contains(searchString) &&
                                                        !textArea.getText().contains(path.toString())) {
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
            }
        }
    }

    private void listFiles(Path path) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    listFiles(entry);
                }
                textArea.append(entry.toString() + "\n");
            }
        }
    }

    private void listFiles(Path path, String searchString) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    listFiles(entry);
                }
                Files.lines(path,
                        Charset.defaultCharset())
                        .forEach(line -> {
                            if (line.contains(searchString) &&
                                    !textArea.getText().contains(path.toString())) {
                                textArea.append(path + "\n");
                            }
                        });
            }
        }
    }
}
