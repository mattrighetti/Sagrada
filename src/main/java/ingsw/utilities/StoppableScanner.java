package ingsw.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.*;

public class StoppableScanner {
    private ExecutorService executorService;
    private Future<String> lineRead;
    private Future<Integer> intRead;

    public String readLine() {
        executorService = Executors.newSingleThreadExecutor();
        String inputLine = null;

        try {
            lineRead = executorService.submit(new ConsoleReadLineTask());

            try {
                inputLine = lineRead.get();
            } catch (IllegalStateException e) {
                System.err.println("Time OUT triggers (Illegal State Exception)");
            } catch (ExecutionException e) {
                System.err.println("Interrupted READ LINE");
            } catch (InterruptedException e) {
                System.err.println("Interrupted READ LINE");
                Thread.currentThread().interrupt();
                executorService.shutdown();
            }

        } finally {
            executorService.shutdown();
        }

        return inputLine;
    }

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
                System.err.println("Time OUT triggers (Illegal State Exception)");
            } catch (ExecutionException e) {
                System.err.println("Interrupted READ INT");
            } catch (InterruptedException e) {
                System.err.println("Interrupted READ INT");
                Thread.currentThread().interrupt();
                executorService.shutdown();
            }

        } finally {
            executorService.shutdown();
        }

        return inputInt;
    }

    public void cancel() {
        if (lineRead != null)
            lineRead.cancel(true);
        if (intRead != null)
            intRead.cancel(true);
    }

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
                    System.err.println("ConsoleLineReader stopped");
                    Thread.currentThread().interrupt();
                    return null;
                }
            } while (input < -1);

            return input;
        }
    }
}
