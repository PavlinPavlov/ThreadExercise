package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultithreadedServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3434);

        while (true) {
            Socket socket = server.accept();
            CustomServerThread customServerThread = new CustomServerThread(socket);
            new Thread(customServerThread).start();
        }
    }
}