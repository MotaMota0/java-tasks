package org.example.socketConnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient2 {
    public static void main(String[] args) {
        String address = "127.0.0.2";
        int port = 8088;
        try(Socket socket = new Socket(address,port)){
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));



            Thread readerThread = new Thread(() -> {
                try{
                    String serverMsg;
                    while((serverMsg = in.readLine()) != null){
                        System.out.println(serverMsg);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            });

            readerThread.start();


            //System.out.println("enter  a message for server:");
            String clientInput;
            while (true){
                clientInput =consoleInput.readLine();
                if(clientInput.equalsIgnoreCase("exit")) break;
                out.println(clientInput);
            }



        }catch (IOException e){
            System.err.println("Err" + e);
        }

    }
}


