package aps.main.controller.config.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class SocketClient extends Thread{
        private ServerSocket server;
        private Socket client;

        Scanner scanner;


        public void init() throws IOException {
            this.server = new ServerSocket(9999);
            if(!this.server.isBound()) {
                server.bind(new InetSocketAddress("127.0.0.1",12345));
            }
            client = this.server.accept();

            scanner = new Scanner(client.getInputStream());


            while (scanner.hasNextLine()){
                System.out.println(scanner.nextLine());

            }

        }
    @Override
    public void run() {
        super.run();

        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
