package edu.colostate.cs.cs414.banqi.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
    private Socket socket;
    private PrintWriter clientOut;
    private ChatServer server;
    private Database db;
    

    public ClientThread(ChatServer server, Socket socket){
        this.server = server;
        this.socket = socket;
        db = new Database("root", "password", "129.82.44.147", "5123"); 
    }

    private PrintWriter getWriter(){
        return clientOut;
    }
    
    public String parseMessage(String string){
    	String success = "";
    	String[] tokens = string.split(";");
    	if(tokens[0].equals("1")){
    		success = executeQuery(tokens[1]);
    	}else if(tokens[0].equals("2")){
    		updateQuery(tokens[1]);
    		success = "Query updated";
    	}
    	return success;
    }

    
    @Override
    public void run() {
        try{
            // setup
            this.clientOut = new PrintWriter(socket.getOutputStream(), false);
            Scanner in = new Scanner(socket.getInputStream());

            // start communicating
            while(!socket.isClosed()){
                if(in.hasNextLine()){
                    String input = in.nextLine();
                    String result = parseMessage(input);
                    if(this.clientOut != null){
                        this.clientOut.write(result + "\r\n");
                        this.clientOut.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String executeQuery(String query) {
        return db.executeQuery(query);
    }
    
    public void updateQuery(String query){
    	db.executeUpdate(query);
    } 
}
