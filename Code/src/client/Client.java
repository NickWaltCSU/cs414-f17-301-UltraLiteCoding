package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
    	Client client = new Client();
	System.out.println(client.sendQuery("SELECT * FROM user"));
    }
    private static final String serverHost = "129.82.44.171";
    private static final int serverPort = 4444;

    //private String userName;
    //private String serverHost;
    //private int serverPort;
    private ServerThread serverThread;
    /*public static void main(String[] args){
        String readName = null;
        while(readName == null || readName.trim().equals("")){
            // null, empty, whitespace(s) not allowed.
            readName = scan.nextLine();
            if(readName.trim().equals("")){
                System.out.println("Invalid. Please enter again:");
            }
        }

        Client client = new Client(readName, host, portNumber);
        client.startClient(scan);
    }*/

    public Client(){
        //this.userName = userName;
        //this.serverHost = host;
        //this.serverPort = portNumber;
	startClient();
    }

    public String sendQuery(String query) {
	String result = "EMPTY";
    	if(serverThread != null) {
 	    System.out.println("added next message");
	    serverThread.addNextMessage(query);
	}
	else {
	    System.out.println("Error: serverthread not active");
	}
	try {
	    Thread.sleep(500);
	}
	catch (InterruptedException ie) {
	    System.out.println(ie.getMessage());
	}
	synchronized(serverThread.receivedMessages) {
	    while(!serverThread.hasReceivedMessages) {
		
	    }
	    result = serverThread.receivedMessages.pop();
	    serverThread.hasReceivedMessages = !serverThread.receivedMessages.isEmpty();
	}
	return result;
    }
    
    public void sendUpdate(String update) {
	

    private void startClient(){
        try{
            Socket socket = new Socket(serverHost, serverPort);
            Thread.sleep(1000); // waiting for network communicating.

            serverThread = new ServerThread(socket);
            Thread serverAccessThread = new Thread(serverThread);
            serverAccessThread.start();
            //while(serverAccessThread.isAlive()){
            	//TODO This is where Client sends messages (as typed into the console)
            	//TODO If you want to send anything else (like a query line), just do serverThread.addNextMessage("QUERY HERE")
                // NOTE: scan.hasNextLine waits input (in the other words block this thread's process).
                // NOTE: If you use buffered reader or something else not waiting way,
                // NOTE: I recommends write waiting short time like following.
                // else {
                //    Thread.sleep(200);
                // }
            //}
        }catch(IOException ex){
            System.err.println("Fatal Connection error!");
            ex.printStackTrace();
        }catch(InterruptedException ex){
            System.out.println("Interrupted");
        }
    }
}
