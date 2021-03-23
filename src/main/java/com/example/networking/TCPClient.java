package com.example.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    private int serverPort;
    private String serverIP;

    public TCPClient(int serverPort, String serverIP) {
        this.serverPort = serverPort;
        this.serverIP = serverIP;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader clientInput =
                new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter server's IP address:");
        String serverIP = clientInput.readLine();
        System.out.println("Please enter server's PORT:");
        int port = Integer.parseInt(clientInput.readLine());
        TCPClient tcpClient = new TCPClient(port,serverIP);
        tcpClient.startClient();
    }

    public void startClient(){
        try(Socket clientSocket = new Socket(serverIP,serverPort);
            BufferedReader clientInput =
                    new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintStream serverOutput = new PrintStream(clientSocket.getOutputStream())) {
            System.out.println("Connection with server is established.");

            while (true){
                System.out.println("Type message to send: ");
                String message = clientInput.readLine();
                serverOutput.println(message);
                String serverResponse = serverInput.readLine();
                System.out.println("Server responded with message: " + serverResponse);
                if("exit".equals(message) || "shutdown".equals(message)){
                    break;
                }
            }
            System.out.println("Closing socket.");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
