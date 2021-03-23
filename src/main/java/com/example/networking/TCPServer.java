package com.example.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private int port;

    public TCPServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer(8081);
        tcpServer.startServer();
    }

    public void startServer(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            boolean isShutdown = false;
            while (!isShutdown) {
                System.out.println("Server is waiting for client requests.");
                System.out.println("Server ip: " + InetAddress.getLocalHost().getHostAddress());

                try (Socket clientConnection = serverSocket.accept(); BufferedReader clientInput = new BufferedReader(
                        new InputStreamReader(clientConnection.getInputStream()));
                     PrintStream clientOutput = new PrintStream(clientConnection.getOutputStream())) {
                    System.out.println("Client connection accepted with ip: " + clientConnection.getInetAddress());
                    while (true) {
                        String clientMessage = clientInput.readLine();
                        System.out.println("Client message: " + clientMessage);
                        if ("exit".equals(clientMessage)) {
                            clientOutput.println("Good bye!");
                            System.out.println("Closing connection with client: " + clientConnection.getInetAddress());
                            break;
                        } else if ("shutdown".equals(clientMessage)) {
                            clientOutput.println("Server shutting down.");
                            System.out.println("Server shutdown by client: " + clientConnection.getInetAddress());
                            isShutdown = true;
                            break;
                        }
                        clientOutput.println("You send this message: " + clientMessage.toUpperCase());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
