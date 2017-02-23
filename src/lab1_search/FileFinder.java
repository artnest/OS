package lab1_search;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import static java.lang.Thread.sleep;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;

class FileFinder extends SimpleFileVisitor<Path> {
    private PathMatcher matcher;
    private JTextArea textArea;
    private List<String> filePaths;
    private String searchString = "";
    private boolean searchInSubdirectories = true;
    private Path initialPath;
    private boolean interrupted;
    private boolean paused;

    FileFinder(String pattern,
               JTextArea textArea, List<String> filePaths,
               boolean interrupted, boolean paused) {
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.textArea = textArea;
        this.filePaths = filePaths;
        this.interrupted = interrupted;
        this.paused = paused;
    }

    FileFinder(String pattern, String searchString,
               JTextArea textArea, List<String> filePaths,
               boolean interrupted, boolean paused) {
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.textArea = textArea;
        this.filePaths = filePaths;
        this.searchString = searchString;
        this.interrupted = interrupted;
        this.paused = paused;
    }

    FileFinder(String pattern, boolean searchInSubdirectories, Path initialPath,
               JTextArea textArea, List<String> filePaths,
               boolean interrupted, boolean paused) {
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.searchInSubdirectories = searchInSubdirectories;
        this.initialPath = initialPath;
        this.textArea = textArea;
        this.filePaths = filePaths;
        this.interrupted = interrupted;
        this.paused = paused;
    }

    FileFinder(String pattern, String searchString, boolean searchInSubdirectories, Path initialPath,
               JTextArea textArea, List<String> filePaths,
               boolean interrupted, boolean paused) {
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.searchString = searchString;
        this.searchInSubdirectories = searchInSubdirectories;
        this.initialPath = initialPath;
        this.textArea = textArea;
        this.filePaths = filePaths;
        this.interrupted = interrupted;
        this.paused = paused;
    }

    @Override
    public FileVisitResult visitFile(Path path,
                                     BasicFileAttributes fileAttributes) throws IOException {
        if (searchString.isEmpty()) {
            find(path);
        } else {
            find(path, searchString);
        }
        return CONTINUE;
    }

    private void find(Path path) {
        Path name = path.getFileName();
        if (name != null && matcher.matches(name)) {
            if (paused) {
                textAreaUpdate(path);
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

            textAreaUpdate(path);
            textArea.append(path.toString() + "\n");
            filePaths.add(path.toString());
        }
    }

    private void find(Path path, String searchString) throws IOException {
        Path name = path.getFileName();
        if (name != null && matcher.matches(name)) {
            Files.lines(path,
                    Charset.defaultCharset())
                    .forEach(line -> {
                        if (line.contains(searchString)) {
                            if (paused) {
                                textAreaUpdate(path);
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

                            textAreaUpdate(path);
                            textArea.append(path + "\n");
                            filePaths.add(path.toString());
                        }
                    });
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path path,
                                             BasicFileAttributes fileAttributes) {
//        find(path); // invoke pattern matching on each directory
        if (!searchInSubdirectories) {
            if (path.equals(initialPath)) {
                return CONTINUE;
            } else {
                return SKIP_SUBTREE;
            }
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        textAreaUpdate(file);
        textArea.append(">>> Access denied: " + exc.getMessage() + "\n");
        return CONTINUE;
    }

    private void textAreaUpdate(Path filePath) {
        textArea.setRows(textArea.getRows() + 1);
        if (textArea.getColumns() < filePath.toString().length()) {
            textArea.setColumns(filePath.toString().length());
        }
    }
}
