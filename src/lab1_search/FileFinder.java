package lab1_search;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

class FileFinder extends SimpleFileVisitor<Path> {
    private PathMatcher matcher;
    private JTextArea textArea;
    private String searchString;

    FileFinder(String pattern, JTextArea textArea) {
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.textArea = textArea;
        this.searchString = "";
    }

    FileFinder(String pattern, String searchString, JTextArea textArea) {
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.textArea = textArea;
        this.searchString = searchString;
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
        find(path);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.err.println(exc.getCause());
        return CONTINUE;
    }
}
