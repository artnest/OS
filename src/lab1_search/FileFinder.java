package lab1_search;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;

class FileFinder extends SimpleFileVisitor<Path> {
    private PathMatcher matcher;
    private JTextArea textArea;
    private String searchString = "";
    private boolean searchInSubdirectories = true;
    private Path initialPath;

    FileFinder(String pattern, JTextArea textArea) {
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.textArea = textArea;
    }

    FileFinder(String pattern, String searchString, JTextArea textArea) {
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.textArea = textArea;
        this.searchString = searchString;
    }

    FileFinder(String pattern, boolean searchInSubdirectories, Path initialPath, JTextArea textArea) {
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.searchInSubdirectories = searchInSubdirectories;
        this.initialPath = initialPath;
        this.textArea = textArea;
    }

    FileFinder(String pattern, String searchString, boolean searchInSubdirectories, Path initialPath, JTextArea textArea) {
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.searchString = searchString;
        this.searchInSubdirectories = searchInSubdirectories;
        this.initialPath = initialPath;
        this.textArea = textArea;
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
        if (name != null && matcher.matches(name))
            textArea.append(path.toString() + "\n");
    }

    private void find(Path path, String searchString) throws IOException {
        Path name = path.getFileName();
        if (name != null && matcher.matches(name)) {
            Files.lines(path,
                    Charset.defaultCharset())
                    .forEach(line -> {
                        if (line.contains(searchString)) {
                            textArea.append(path + "\n");
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
        textArea.append(exc.getCause() + "\n");
        return CONTINUE;
    }
}
