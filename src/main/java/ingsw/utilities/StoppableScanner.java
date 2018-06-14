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
            } catch (ExecutionException e) {
                System.err.println("Interrupted READ LINE");
            } catch (InterruptedException e) {
                System.err.println("Interrupted READ LINE");
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
            } catch (ExecutionException e) {
                System.err.println("Interrupted READ INT");
            } catch (InterruptedException e) {
                System.err.println("Interrupted READ INT");
                executorService.shutdown();
            }

        } finally {
            executorService.shutdown();
        }

        return inputInt;
    }

    private void cancel() {
        lineRead.cancel(true);
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

                    input = bufferedReader.read();

                } catch (InterruptedException e) {
                    System.err.println("ConsoleLineReader stopped");
                    return null;
                }
            } while (input < 0);

            return input;
        }
    }
}
