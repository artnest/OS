package lab2_threads;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by theme on 26/04/17.
 */
public class InputThread extends Thread {

    public static Queue<File> fileQueue = new ArrayDeque<>();

    public InputThread() {
    }

    @Override
    public void run() {
        while (!fileQueue.isEmpty()) {
            File file = fileQueue.poll();

            // TODO add data check

            ExecutionThread.fileQueue.add(file);
        }
    }
}
