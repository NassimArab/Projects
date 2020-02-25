package fil.car;

import java.io.File;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    private static int PORT = 1030;
    
    public static void main(String... args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {

            
        	
            Socket clientSocket = serverSocket.accept();
            if(clientSocket != null)
            new Thread(new FtpRequest(clientSocket)).start();
            
            
        }
    }
}