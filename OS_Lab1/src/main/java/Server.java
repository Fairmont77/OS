import java.io.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

public class Server {
    private static  Integer x; //static variable for storing the entered value x

    private static Process funcF; // processes for performing functions F and G

    private static Process funcG;

    private static int clientsToWaitFor = 2; // counter of clients to connect to the server

    // server constructor that initializes input and starts server logic
    public Server()  {
        Scanner sc = new Scanner(System.in);
        int x = 0;
        System.out.println("Please, enter x: ");
        if (sc.hasNextInt()) {
            x = sc.nextInt();
        }
        sc.nextLine();
        this.x = x;

        run();
    }

    //main process which run the server
    public static void run()  {
        ArrayList<String> receivedValues = new ArrayList<>();

        initProcesses();

        File socketFile = new File(new File(System.getProperty("java.io.tmpdir")), "junixsocket-test.sock");
        try (AFUNIXServerSocket serverSocket = AFUNIXServerSocket.newInstance()) {
            serverSocket.bind( AFUNIXSocketAddress.of(socketFile));
            System.out.println("Server is running. Waiting for connections...");

            while (clientsToWaitFor > 0) {
                Socket clientSocket = serverSocket.accept();

                try (OutputStream outputStream = clientSocket.getOutputStream()) {
                    outputStream.write(Integer.toString(x).getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String clientMessage = reader.readLine();
                    if (clientMessage!=null) {
                        receivedValues.add(clientMessage);
                        clientsToWaitFor--;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        parseResult(receivedValues);
    }

    //main binary operation
    private static double binaryOperation(double resultF, double resultG){
        return resultF + resultG;
    }

    //method for analyzing the results
    private static void parseResult(List<String> array){
        String elemF = array.get(0);
        String elemG = array.get(1);

        if (elemF.charAt(1) == 'c') {
            System.out.println("Critical failure of function F");

        }else if(elemG.charAt(1) == 'c') {
            System.out.println("Critical failure of function G");

        }else if (elemF.charAt(1) == 't') {
            System.out.println("Function F execution limit exceeded");

        }else if(elemG.charAt(1) == 't') {
            System.out.println("Function G execution limit exceeded");

        }else {
            double resultF = Double.parseDouble(elemF.substring(1));
            double resultG = Double.parseDouble(elemG.substring(1));

            System.out.println("The result of a binary operation = " + binaryOperation(resultF, resultG));
        }
    }

    //method for initializing the processes of functions F and G
    private static void initProcesses()  {

        ProcessBuilder processBuilderF = new ProcessBuilder("java", "ClientF.java");
        ProcessBuilder processBuilderG = new ProcessBuilder("java", "ClientG.java");
        Executor executor = Executors.newFixedThreadPool(2);

        Runnable runnableF = () -> {
            try {
                funcF = processBuilderF.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Runnable runnableG = () -> {
            try {
                funcG = processBuilderG.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        executor.execute(runnableF);
        executor.execute(runnableG);

    }
}
