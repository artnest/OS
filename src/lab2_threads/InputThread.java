package lab2_threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by theme on 26/04/17.
 */
public class InputThread extends Thread {

    public static Queue<File> fileQueue = new ArrayDeque<>();
    private static boolean isFilesCheckFinished = false;

    public InputThread() {
    }

    @Override
    public void run() {
        while (!fileQueue.isEmpty()) {
            File file = fileQueue.poll();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                try {
                    int N = Integer.parseInt(reader.readLine());
                    if (N >= 1 && N <= Math.pow(10, 7)) {
                        ExecutionThread.fileQueue.put(file);
                    } else {
//                        textPane.setText("Incorrect input data (N): " + file.getCanonicalPath());
                    }
                } catch (NumberFormatException e) {
//                    textPane.setText("Invalid input data: " + file.getCanonicalPath());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
//                textPane.setText("Could not read file: " + file.getAbsolutePath());
            }
        }

        isFilesCheckFinished = true;
    }

    public static boolean isFilesCheckFinished() {
        return isFilesCheckFinished;
    }
}
