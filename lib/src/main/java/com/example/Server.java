package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

/**
 * Created by user on 2016/6/11.
 */
public class Server implements Runnable{
    private MyWindow window;

    private Thread thread;
    private ServerSocket servSock;

    private ConnectionThread connection;

    public Server(){
        this.window = new MyWindow();

        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            System.out.println("IP of my system is := "+IP.getHostAddress());

            // Create server socket
            this.servSock = new ServerSocket(8000);
            int portNum = this.servSock.getLocalPort();
            System.out.println("Waitting to connect.......");

            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
    }

    @Override
    public void run(){
        while(true){
            try{
                Socket cliSock = servSock.accept();
                System.out.println("Connected from " + cliSock.getLocalAddress());
                this.connection = new ConnectionThread(cliSock);
                this.connection.start();
            }
            catch(Exception e){
                System.out.println("Error: "+e.getMessage());
            }
        }
    }

    class ConnectionThread extends Thread{
        private Socket socket;
        private BufferedReader reader;

        ConnectionThread(Socket socket){
            this.socket = socket;
            try{
                this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        public void run(){
           while(true){
               try{
                   String line = this.reader.readLine();
                   window.textarea.setText(line);
               }
               catch (IOException e){
                   e.printStackTrace();
               }
           }
        }
    }


    static public void main(String[] args){
        Server server = new Server();
    }
}
