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
    private JButton startButton;
    private JButton pauseButton;
    private JButton stopButton;
    private List<String> filePaths = new LinkedList<>();

    private final Object lock = new Object();
    private boolean interrupted = false;
    private boolean paused = false;

    SearchThread(JTextArea textArea,
                 JButton startButton, JButton pauseButton, JButton stopButton) {
        this.textArea = textArea;
        this.startButton = startButton;
        this.pauseButton = pauseButton;
        this.stopButton = stopButton;
    }

    SearchAttributes getSearchAttributes() {
        return searchAttributes;
    }

    void setSearchAttributes(SearchAttributes searchAttributes) {
        this.searchAttributes = searchAttributes;
    }

    void setButtons() {
        startButton.setEnabled(true);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
    }

    List<String> getFilePaths() {
        return filePaths;
    }

    void interruptThread() {
        this.interrupted = true;
    }

    boolean isPaused() {
        return paused;
    }

    void pause() {
        this.paused = true;
    }

    void unpause() {
        this.paused = false;
    }

    @Override
    public void run() {
        boolean searchInSubdirectories = searchAttributes.isSubdirectory();
        boolean searchByPattern = searchAttributes.isPattern();
        boolean searchForSubstring = searchAttributes.isSubstring();

        textArea.setRows(1);
        textArea.setColumns(256);

        if (!searchInSubdirectories &&
                !searchByPattern &&
                !searchForSubstring) {
            try {
    /*
                    Files.list(Paths.get(searchAttributes.getPath()))
                            .filter(Files::isRegularFile)
                            .forEach(path -> textArea.append(path + "\n"));
    */
                synchronized (lock) {
                    Files.newDirectoryStream(Paths.get(searchAttributes.getPath()),
                            path -> path.toFile().isFile())
                            .forEach(path -> {
                                if (paused) {
                                    updateTextArea(path);
                                    textArea.append(">>> Search paused\n");

                                    while (paused) {
                                        try {
                                            sleep(1000);
                                        } catch (InterruptedException e) {
                                            System.err.println(e.getMessage());
                                        }
                                    }
                                }

                                if (interrupted) {
                                    return;
                                }

                                updateTextArea(path);
                                textArea.append(path + "\n");
                                filePaths.add(path.toString());
                            });
                }
            } catch (IOException e) {
                printException(e);
            }
        } else if (searchInSubdirectories &&
                !searchByPattern &&
                !searchForSubstring) {
                listFiles(Paths.get(searchAttributes.getPath()));
        } else if (searchInSubdirectories &&
                searchByPattern &&
                !searchForSubstring) {
            try {
                synchronized (lock) {
                    Files.walkFileTree(Paths.get(searchAttributes.getPath()),
                            new FileFinder(searchAttributes.getPattern(),
                                    textArea, filePaths, interrupted, paused));
                }
            } catch (IOException e) {
                printException(e);
            }
        } else if (searchInSubdirectories &&
                !searchByPattern &&
                searchForSubstring) {
                listFiles(Paths.get(searchAttributes.getPath()), searchAttributes.getSubstring());
        } else if (searchInSubdirectories &&
                searchByPattern &&
                searchForSubstring) {
            try {
                synchronized (lock) {
                    Files.walkFileTree(Paths.get(searchAttributes.getPath()),
                            new FileFinder(searchAttributes.getPattern(), searchAttributes.getSubstring(),
                                    textArea, filePaths, interrupted, paused));
                }
            } catch (IOException e) {
                printException(e);
            }
        } else if (!searchInSubdirectories &&
                searchByPattern &&
                !searchForSubstring) {
            try {
                synchronized (lock) {
                    Files.walkFileTree(Paths.get(searchAttributes.getPath()),
                            new FileFinder(searchAttributes.getPattern(),
                                    searchInSubdirectories, Paths.get(searchAttributes.getPath()),
                                    textArea, filePaths, interrupted, paused));
                }
            } catch (IOException e) {
                printException(e);
            }
        } else if (!searchInSubdirectories &&
                searchByPattern &&
                searchForSubstring) {
            try {
                synchronized (lock) {
                    Files.walkFileTree(Paths.get(searchAttributes.getPath()),
                            new FileFinder(searchAttributes.getPattern(),
                                    searchAttributes.getSubstring(),
                                    searchInSubdirectories, Paths.get(searchAttributes.getPath()),
                                    textArea, filePaths, interrupted, paused));
                }
            } catch (IOException e) {
                printException(e);
            }
        } else if (!searchInSubdirectories &&
                !searchByPattern &&
                searchForSubstring) {
            try {
                String searchString = searchAttributes.getSubstring();
                synchronized (lock) {
                    Files.newDirectoryStream(Paths.get(searchAttributes.getPath()),
                            path -> path.toFile().isFile() && path.toString().endsWith(".txt"))
                            .forEach(path -> {
                                try {
                                    Files.lines(path,
                                            Charset.defaultCharset())
                                            .forEach(line -> {
                                                if (line.contains(searchString) &&
                                                        !textArea.getText().contains(path.toString())) {
                                                    if (paused) {
                                                        updateTextArea(path);
                                                        textArea.append(">>> Search paused\n");

                                                        while (paused) {
                                                            try {
                                                                sleep(1000);
                                                            } catch (InterruptedException e) {
                                                                System.err.println(e.getMessage());
                                                            }
                                                        }
                                                    }

                                                    if (interrupted) {
                                                        return;
                                                    }

                                                    updateTextArea(path);
                                                    textArea.append(path + "\n");
                                                    filePaths.add(path.toString());
                                                }
                                            });
                                } catch (IOException e) {
                                    printInternalException(e);
                                }
                            });
                }
            } catch (IOException e) {
                printException(e);
            }
        }

        if (!interrupted) {
            textArea.append(">>> Search completed\n");
        } else {
            textArea.append(">>> Search stopped\n");
            interrupt();
        }
    }

    private void listFiles(Path path) {
        synchronized (lock) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path entry : stream) {
                    if (paused) {
                        updateTextArea(path);
                        textArea.append(">>> Search paused\n");

                        while (paused) {
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                System.err.println(e.getMessage());
                            }
                        }
                    }

                    if (interrupted) {
                        return;
                    }

                    if (Files.isDirectory(entry)) {
                        listFiles(entry);
                    }

                    updateTextArea(entry);
                    textArea.append(entry.toString() + "\n");
                    filePaths.add(path.toString());
                }
            } catch (IOException e) {
                textArea.append(">>> Access denied: " + e.getMessage() + "\n");
            }
        }
    }

    private void listFiles(Path path, String searchString) {
        synchronized (lock) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path entry : stream) {
                    if (Files.isDirectory(entry)) {
                        listFiles(entry, searchString);
                    }
                    Files.lines(path,
                            Charset.defaultCharset())
                            .forEach(line -> {
                                if (line.contains(searchString) &&
                                        !textArea.getText().contains(path.toString())) {
                                    if (paused) {
                                        updateTextArea(path);
                                        textArea.append(">>> Search paused\n");

                                        while (paused) {
                                            try {
                                                sleep(1000);
                                            } catch (InterruptedException e) {
                                                System.err.println(e.getMessage());
                                            }
                                        }
                                    }

                                    if (interrupted) {
                                        return;
                                    }

                                    updateTextArea(path);
                                    textArea.append(path + "\n");
                                    filePaths.add(path.toString());
                                }
                            });
                }
            } catch (IOException e) {
                textArea.append(">>> Access denied: " + e.getMessage() + "\n");
            }
        }
    }

    private void updateTextArea(Path filePath) {
        textArea.setRows(textArea.getRows() + 1);
        if (textArea.getColumns() < filePath.toString().length()) {
            textArea.setColumns(filePath.toString().length());
        }
    }

    void invalidateTextArea() {
        textArea.setText(null);
        textArea.setRows(0);
        textArea.setColumns(0);
    }

    private void printInternalException(IOException e) {
        textArea.setRows(textArea.getRows() + 1);
        textArea.append(">>> Access denied: " + e.getMessage() + "\n");
    }

    private void printException(IOException e) {
        textArea.setRows(textArea.getRows() + 1);
        textArea.append(">>> Access denied: " + e.getMessage() + "\n");
        textArea.append(">>> Search interrupted\n");
    }
}
