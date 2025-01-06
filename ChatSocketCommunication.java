import java.io.*;

public class ChatSocketCommunication extends SocketCommunication {

    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    ChatSocketCommunication(){

    }

    ChatSocketCommunication(CommunicationRole type){
        super(type);
    }

    public void sendMessage(Message message) {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Message getNewMessages() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            Message message1 = (Message) in.readObject();
            System.out.println(message1.getMessageText());
            return message1;

        } catch (Exception e) {
            return new Message(null, null, "");
        }
    }
}
