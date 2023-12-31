package Сlients;

import Functions.Functions;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import java.io.*;
import java.util.Optional;
import java.util.concurrent.*;

public class Client {
    private final File socketFile = new File(new File(System.getProperty("java.io.tmpdir")), "junixsocket-test.sock"); // path to the Unix socket in the temporary directory
    private Integer x; //input value

    private final long timeLimit = 1;  // limiting the time for calculations in seconds

    // client constructor initiates reading an incoming message and starting calculations
    public Client(String typeOfFunction) {
        readMessage();

        long startTime = System.currentTimeMillis();
        String resultStr = typeOfFunction + "t";
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Optional<Double>> future = executorService.submit(() -> {
            switch (typeOfFunction) {
                case "F":
                    return Functions.functionF(x);
                case "G":
                    return Functions.functionG(x);
                default:
                    return Optional.empty();
            }
        });

        try {
            Optional<Double> result = future.get(timeLimit, TimeUnit.SECONDS);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            resultStr = result.map(integer -> typeOfFunction + integer).orElseGet(() -> typeOfFunction + "c");

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Execution timed out. ");
        }

        sendMessage(resultStr, typeOfFunction);
        executorService.shutdownNow();
    }

    // reading the input value x from the server
    public void readMessage() {
        // trying to establish connection with socket and reading data
        try (AFUNIXSocket socket = AFUNIXSocket.newInstance()) {
            socket.connect( AFUNIXSocketAddress.of(socketFile));
            System.out.println("Connected to server");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String serverResponse = reader.readLine();
                System.out.println("Received number from server: " + serverResponse);
                this.x=Integer.valueOf(serverResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // sending the calculation result back to the server
    private void sendMessage(String resultStr, String typeOfFunction){
        try (AFUNIXSocket socket = AFUNIXSocket.newInstance()) {
            socket.connect( AFUNIXSocketAddress.of(socketFile));
            try (OutputStream outputStream = socket.getOutputStream()) {
                outputStream.write(resultStr.getBytes());

                if (resultStr.charAt(1) == 'c'){
                    resultStr = "cancel";

                } else if (resultStr.charAt(1) == 't'){
                    resultStr = "time out";

                }else{
                    resultStr = resultStr.substring(1);
                }

                System.out.println("Sent result to server: " + resultStr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
