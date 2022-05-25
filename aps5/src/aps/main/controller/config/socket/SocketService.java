package aps.main.controller.config.socket;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Scanner;

public class SocketService extends Thread{

    ServerSocket server;
    Socket client;

    Scanner scanner;
    public void configure(int port){
        try {
            this.server = new ServerSocket(3322);
            if (!server.isBound()){
                server.bind(new InetSocketAddress("192.168.0.1", 0));
            }
            client = server.accept();


            scanner = new Scanner(client.getInputStream());

            System.out.println(client.getLocalAddress());



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run() {
        configure(12345);
    }
    public byte[] toBit(String content){
        return content.getBytes();
    }




   // public static void main(String[] args) throws IOException, ClassNotFoundException {

     //new SocketService().run();
    //}

    public void main(String[] args) {
        System.out.println( this.toBit("oie"));
    }

}
