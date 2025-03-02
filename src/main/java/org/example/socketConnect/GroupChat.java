package org.example.socketConnect;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class GroupChat {

    private static final Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        int port = 8088;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server chat wait connect...");

            while (true) {
                Socket clientSocket = server.accept();
                new ClientHandler(clientSocket).start();
                System.out.println("Client was join " + clientSocket.getInetAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

static class ClientHandler extends Thread {
    private Socket clientSocket;

    private PrintWriter pw;
    private BufferedReader bf;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            bf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            pw = new PrintWriter(clientSocket.getOutputStream(), true);

            synchronized (clientWriters){
                clientWriters.add(pw);
            }

            String message;
            while ((message = bf.readLine()) != null) {
                System.out.println("Client " + clientSocket.getInetAddress() + ":" + message);
                sendMessage("Client "+ clientSocket.getInetAddress()+":"+message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (clientWriters){
                clientWriters.remove(pw);
            }
            System.out.println("Client " + clientSocket.getInetAddress() + " off");
        }
    }
    private void sendMessage(String message){
        synchronized (clientWriters){
            for (PrintWriter writer: clientWriters){
                writer.println(message);
            }
        }
    }
}


}
    /*public static void main(String[] args) {
        URL bsu = null;
        String name = "http://www.bsu.by";
        try{
            bsu = new URL(name);

        }catch (MalformedURLException e ){
            e.printStackTrace();
        }


        try(
            BufferedReader bf = new BufferedReader(new InputStreamReader(bsu.openStream()))){
            String line = "";
            while ((line = bf.readLine()) != null){
                System.out.println(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }*/
