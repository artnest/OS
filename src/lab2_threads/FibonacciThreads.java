package lab2_threads;

/**
 * Created by theme on 26/04/17.
 */
public class FibonacciThreads {

    private static InputThread inputThread;
    private static ExecutionThread executionThread;
    private static OutputThread outputThread;

    public static void initInputThread() {
        inputThread = new InputThread();
        inputThread.start();
    }

    public static void initExecutionThread() {
        executionThread = new ExecutionThread();
        executionThread.start();
    }

    public static void initOutputThread() {
        outputThread = new OutputThread();
        outputThread.start();
    }

    public static void initWorkingThreads() {
        inputThread = new InputThread();
        inputThread.start();
        executionThread = new ExecutionThread();
        executionThread.start();
    }

    public static InputThread getInputThread() {
        return inputThread;
    }

    public static ExecutionThread getExecutionThread() {
        return executionThread;
    }

    public static OutputThread getOutputThread() {
        return outputThread;
    }
}
