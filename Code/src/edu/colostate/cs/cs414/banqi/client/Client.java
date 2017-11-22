package edu.colostate.cs.cs414.banqi.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String serverHost = "129.82.44.171";
    private static final int serverPort = 4444;
    
    private Socket socket;
    private MessageHandler messageHandler;

    public static void main(String[] args) {
        Client client = new Client();
        System.out.println(client.sendQuery("1;SELECT * FROM user;"));
    }
    public Client(){
        try {
	        socket = new Socket(serverHost, serverPort);
            messageHandler = new MessageHandler(socket);
        }
        catch(IOException ex) {
            System.err.println("Fatal Connection error!");
            ex.printStackTrace();
        }
    }

    public String sendQuery(String query) {
        return messageHandler.sendMessage(query);
    }
       
}
