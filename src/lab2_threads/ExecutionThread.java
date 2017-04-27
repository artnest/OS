package lab2_threads;

import java.io.*;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by theme on 26/04/17.
 */
public class ExecutionThread extends Thread {

    public static SynchronousQueue<File> fileQueue = new SynchronousQueue<>();
    private static boolean isTasksFinished = false;

    public ExecutionThread() {
    }

    @Override
    public void run() {
        while (true) {
            if (InputThread.isFilesCheckFinished() && fileQueue.isEmpty()) {
                break;
            }

            File file;
            try {
                file = fileQueue.take();
                int N = 0;

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    N = Integer.parseInt(reader.readLine());
                } catch (IOException e) {
//                    textPane.setText("Could not read file: " + file.getAbsolutePath());
                }

                StringBuilder seq = new StringBuilder();
                boolean firstSequence = true;
                int number = 0;
                int i = 0;
                for (int step = 1, index = 0; i < N; number++, i += step, index++) {
                    if (firstSequence) {
                        if (index == 6) {
                            index = 0;
                            step++;
                            firstSequence = false;
                        }
                    } else {
                        if (index == 5) {
                            index = 0;
                            step++;
                        }
                    }

                    if (i + step > N) {
                        break;
                    }
                }

                for (; i < N; i++) {
                    number++;
                }

                for (int j = 1; j <= number; j++) {
                    seq.append(fib(j));
                }

                int result = Integer.parseInt(seq.substring(N - 1));

                String filePath = file.getParent() + File.separator +
                        "_" + file.getName().substring(0, file.getName().indexOf('.') + 1)
                        + "out";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    writer.write(String.valueOf(result));
                    OutputThread.fileSuccessQueue.put(new File(filePath)); // check
                } catch (IOException e) {
                    // textPane.setText("Could not create output file");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isTasksFinished = true;
    }

    private long fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n <= 2) {
            return 1;
        }

        long[] dp = new long[n + 1];
        dp[1] = 1;
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    public static boolean isTasksFinished() {
        return isTasksFinished;
    }
}
