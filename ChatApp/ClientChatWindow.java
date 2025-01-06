
public class ClientChatWindow {

    public static void main(String[] args) {
        ChatSocketCommunication client = new ChatSocketCommunication();
        client.startClientConnection(1902);
        ChatWindow window = new ChatWindow(client);
        window.open();
    }

}
