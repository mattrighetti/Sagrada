package ingsw.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.*;

/**
 * Scanner which can be interrupted by a time-out signal.
 */
public class StoppableScanner {
    private ExecutorService executorService;
    private Future<String> lineRead;
    private Future<Integer> intRead;

    /**
     * Reads a Line
     * @return Line read
     */
    public String readLine() {
        executorService = Executors.newSingleThreadExecutor();
        String inputLine = null;

        try {
            lineRead = executorService.submit(new ConsoleReadLineTask());

            try {
                inputLine = lineRead.get();
            } catch (IllegalStateException e) {
                System.err.println("Wrong Input");
                inputLine = "interrupted";
            } catch (ExecutionException e) {
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                executorService.shutdown();
                inputLine = "interrupted";
            }

        } finally {
            executorService.shutdown();
        }

        return inputLine;
    }

    /**
     * Read int
     * @return read int
     */
    public Integer readInt() {
        executorService = Executors.newSingleThreadExecutor();
        Integer inputInt = 0;

        try {
            intRead = executorService.submit(new ConsoleReadIntTask());

            try {
                inputInt = intRead.get();
            } catch (NumberFormatException e) {
                System.err.println("Type again");
                inputInt = -1;
            } catch (IllegalStateException e) {
                System.err.println("Wrong Input");
                inputInt = -1;
            } catch (ExecutionException e) {
                inputInt = -1;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                inputInt = -1;
            }

        } finally {
            executorService.shutdown();
        }

        return inputInt;
    }

    /**
     * Cancels the scanner
     */
    public void cancel() {
        if (lineRead != null)
            lineRead.cancel(true);
        if (intRead != null)
            intRead.cancel(true);
    }

    /**
     * Check if the scanner is cancelled
     * @return Return true if it is cancelled
     */
    public boolean isReaderCancelled() {
        return intRead == null;
    }

    /**
     * Task that reads a Line and can be interrupted by a time-out signal
     */
    class ConsoleReadLineTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String input;
            do {
                System.out.println("Waiting for input: ");
                try {
                    while (!bufferedReader.ready()) {
                        Thread.sleep(200);
                    }

                    input = bufferedReader.readLine();

                } catch (InterruptedException e) {
                    System.err.println("ConsoleLineReader stopped");
                    Thread.currentThread().interrupt();
                    return null;
                }
            } while ("".equals(input));

            return input;
        }
    }

    /**
     * Task that reads a int and can be interrupted by a time-out signal
     */
    class ConsoleReadIntTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Integer input;
            do {
                System.out.println("Waiting for input: ");
                try {
                    while (!bufferedReader.ready()) {
                        Thread.sleep(200);
                    }

                    input = Integer.parseInt(bufferedReader.readLine());

                } catch (InterruptedException e) {
                    System.err.println("ConsoleIntReader stopped");
                    Thread.currentThread().interrupt();
                    return null;
                }
            } while (input < -1);

            return input;
        }
    }
}
