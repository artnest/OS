package lab2_threads;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by theme on 26/04/17.
 */
public class OutputThread extends Thread {

    public static Queue<File> fileQueue = new ArrayDeque<>();
    public static SynchronousQueue<File> fileSuccessQueue = new SynchronousQueue<>();

    public OutputThread() {
    }

    @Override
    public void run() {
        File patternFile = fileQueue.poll();
        File file = null;
        try {
            file = fileSuccessQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*byte[] f1 = Files.readAllBytes(Paths.get(patternFile.getCanonicalPath()));
        byte[] f2 = Files.readAllBytes(Paths.get(file.getCanonicalPath()));*/

        int patternResult = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(patternFile))) {
            patternResult = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
//            textPane.setText("Could not read file");
        }

        int result = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            result = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
//            textPane.setText("Could not read file");
        }

        if (result == patternResult) {
            System.out.println("Results match! The task was executed successfully!");
        } else {
            System.out.println("Results do not match!");
        }

        // add to results' queue in MainForm
    }
}
