package com.example.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public TCPServer(int port) {
        this.port = port;
    }

    private int port;

    public void startServer()
    {
        try (ServerSocket ss=new ServerSocket(port))
        {
            System.out.println("Server is waiting for client request");
            try(Socket clientConnection=ss.accept();
                BufferedReader clientInput =new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
                PrintStream clientOutput =new PrintStream(clientConnection.getOutputStream())
                )
            {
                System.out.println("client connection accept with ip "+ clientConnection.getInetAddress());
                while (true){
                    String clientmessage=clientInput.readLine();
                    System.out.println("SMessage" +clientmessage);
                    if ("exit".equals(clientmessage))
                    {
                        clientOutput.println("Goodbye");
                        System.out.println("Closing");
                        break;
                    }
                    else if("shutdown".equals(clientmessage))
                    {clientOutput.println("shutdown");
                        System.out.println("Server shutdown");
                        break;}

               clientOutput.println("cccc"+clientmessage.toUpperCase());
                }

            }

        }
        catch (IOException e)
        {e.printStackTrace();}


    }




}
