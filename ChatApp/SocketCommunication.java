import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;

public class SocketCommunication {
    
    protected Socket socket;
    protected CommunicationRole type;

    SocketCommunication(){
        
    }

    SocketCommunication(CommunicationRole type){
        this.type = type;
    }

    public void startServerConnection(int port){
        type = CommunicationRole.SERVER;
        try {
            ServerSocket server = new ServerSocket(port);
            socket = server.accept();
            System.out.println("Accepted a client!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startClientConnection(int port){
        type = CommunicationRole.CLIENT;
        try {
            socket = new Socket("localhost", port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void startConnectionBasedOnType(int port){
        if(type == CommunicationRole.SERVER){
            this.startServerConnection(port);
        }
        else {
            this.startClientConnection(port);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setType(CommunicationRole type) {
        this.type = type;
    }

    public CommunicationRole getType() {
        return type;
    }

    public Socket getSocket() {
        return socket;
    }

}
