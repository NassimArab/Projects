package fil.car;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client implements Runnable {

    private BufferedReader in;
    private PrintStream out; // out.println(...)
    
    public Client(Socket socket) throws IOException {
    	
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintStream(socket.getOutputStream());
        
        System.out.println("connected");
    	
    	out.print("welcome, you're connected with us!");
    }

    @Override
    public void run() {
        while (true) {
            
            	try {
					process(in.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
    }

    private static void process(String request) {
        System.out.println(request);
      
    }
}