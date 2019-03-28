package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientHandler implements Runnable {

    private Socket client;
    private PrintWriter clientOutput;
    private BufferedReader clientInput;
    private boolean isConnected;
    public ClientHandler (Socket client) {
        this.client = client;
        this.isConnected = client.isConnected();

    }


    @Override
    public void run() {

        try {
            var output = new PrintWriter(this.client.getOutputStream(), true);
            var input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            this.clientOutput = output;
            this.clientInput = input;

            String message;

            while ((message = this.clientInput.readLine()) != null) {
                //MessageBroadcaster.broadcast(this);
                output.println(message);
                //System.out.println(input.readLine());
            }
        } catch (IOException e) {
            System.err.println("Socket disconnected");
        } finally {
            this.isConnected = false;
        }
    }

    public PrintWriter getClientOutput() {
        return this.clientOutput;
    }

    public BufferedReader getClientInput() {
        return this.clientInput;
    }

    public SocketAddress getSocketAddress() {
        return this.client.getRemoteSocketAddress();
    }



    public boolean isConnected() {
        return this.isConnected;
    }
}
