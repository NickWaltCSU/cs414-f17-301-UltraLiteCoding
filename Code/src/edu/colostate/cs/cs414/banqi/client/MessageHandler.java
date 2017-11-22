package edu.colostate.cs.cs414.banqi.client;


import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class MessageHandler {
    private Socket socket;
    private PrintWriter serverOut;
    private InputStream serverInStream;
    private Scanner serverIn;

    public MessageHandler(Socket socket){
        this.socket = socket;
        try {
            serverOut = new PrintWriter(socket.getOutputStream(), false);
            serverInStream = socket.getInputStream();
            serverIn = new Scanner(serverInStream);
        }
        catch(IOException ioe) {
            System.err.println("Fatal Connection error!");
            ioe.printStackTrace();
        }
    }

    public String sendMessage(String message){
        String received = " ";
        if(!socket.isClosed()) {
            serverOut.println(message);
            serverOut.flush();
        
            if(serverIn.hasNextLine()){
                received = serverIn.nextLine();
            }
        }
        return received;
    }
}
