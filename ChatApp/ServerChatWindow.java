import java.net.*;

public class ServerChatWindow {

    public static void main(String[] args) {
        ChatSocketCommunication chatUserClient = new ChatSocketCommunication();
        chatUserClient.startServerConnection(1902);
        ChatWindow window = new ChatWindow(chatUserClient);
        window.open();
    }
}
