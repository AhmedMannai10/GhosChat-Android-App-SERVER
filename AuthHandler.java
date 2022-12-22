package com.content.xchat_app;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import entity.User;

public class AuthHandler implements Runnable , Serializable {

    static final String SERVER_IP = "192.168.1.12";
    static final int PORT = 5000;

    static ObjectOutputStream objectWriter;
    static BufferedReader reader;
    static Socket sock;

    public void run(){
        System.out.println("************GO GO*******************");
        setUpNetworking();
    }

    private void setUpNetworking() {
        try{

            System.out.println("************SETTING UP CONNECTION *******************");
            sock = new Socket(SERVER_IP, PORT);
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            objectWriter = new ObjectOutputStream(sock.getOutputStream());
            System.out.println("Networking established");

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean login(User user){
        System.out.println("login user " + user);
        try{
            ResponseFromServer responseWork = new ResponseFromServer();
            Thread responseThread = new Thread(responseWork);
            responseThread.start();

            objectWriter.writeUnshared(user);
            System.out.println("Object Send");
            objectWriter.flush();

            responseThread.join();
            System.out.println( "-------------" + responseWork.getResponse());
            return responseWork.getResponse();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }



    public class ResponseFromServer implements Runnable {
        private volatile boolean response;
        public void run(){
            String message;
            try{
                message = reader.readLine();
                System.out.println("Response from " + message);
                response = Boolean.parseBoolean(message);

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        public boolean getResponse(){
            return this.response;
        }
    }


}
