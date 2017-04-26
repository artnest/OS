package lab2_threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by theme on 26/04/17.
 */
public class ExecutionThread extends Thread {

    public static Queue<File> fileQueue = new SynchronousQueue<>();

    public ExecutionThread() {
    }

    @Override
    public void run() {
        while (!fileQueue.isEmpty()) {
            File file = fileQueue.poll();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileQueue.poll()))) {
                int N = Integer.parseInt(reader.readLine());

                // TODO add execution code
            } catch (IOException ignored) {
            }
        }

        /*while (fileQueue.isEmpty()) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            if (fileQueue.isEmpty()) {
                // interrupt();
                break;
            }
            fileQueue.take();
            // code
        }*/
    }
}
