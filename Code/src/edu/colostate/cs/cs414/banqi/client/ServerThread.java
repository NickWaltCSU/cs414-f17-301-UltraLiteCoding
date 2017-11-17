package edu.colostate.cs.cs414.banqi.client;


import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class ServerThread implements Runnable {
    private Socket socket;
    private boolean isAlived;
    private final LinkedList<String> messagesToSend;
    public final LinkedList<String> receivedMessages;
    private boolean hasMessages = false;
    public boolean hasReceivedMessages = false;

    public ServerThread(Socket socket){
        this.socket = socket;
        messagesToSend = new LinkedList<String>();
	receivedMessages = new LinkedList<String>();
    }

    public void addNextMessage(String message){
        synchronized (messagesToSend){
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

    public void addReceivedMessage(String message) {
	synchronized (receivedMessages) {
	    hasReceivedMessages = true;
	    receivedMessages.push(message);
	}
    }


    @Override
    public void run(){
        System.out.println("Local Port :" + socket.getLocalPort());
        System.out.println("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());

        try{
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);
            // BufferedReader userBr = new BufferedReader(new InputStreamReader(userInStream));
            // Scanner userIn = new Scanner(userInStream);
            while(!socket.isClosed()){
                if(serverInStream.available() > 0){
                    if(serverIn.hasNextLine()){
                    	String received = serverIn.nextLine();
                        synchronized(receivedMessages) {
			    receivedMessages.push(received);
			    hasReceivedMessages = !receivedMessages.isEmpty();
			}
                    }
                }
                if(hasMessages){
                    String nextSend = "";
                    synchronized(messagesToSend){
                        nextSend = messagesToSend.pop();
                        hasMessages = !messagesToSend.isEmpty();
                    }
                    serverOut.println(nextSend);
                    serverOut.flush();
                }
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

    }
}
