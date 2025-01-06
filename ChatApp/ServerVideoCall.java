// import java.net.*;

// public class ServerVideoCall {

//     public static void main(String args[]) {

//         try {
//             ServerSocket server = new ServerSocket(1902);
//             Socket socket = server.accept();
//             System.out.println(socket.getInetAddress().getHostAddress());
//             System.out.println(socket.getPort());
//             System.out.println("A client has been accepted!");
//             ChatUser chatUser = new ChatUser(socket, "Server");
//             VideoCallWindow window = new VideoCallWindow(chatUser);
//             window.open();
//         } catch (Exception e) {

//         }

//     }
// }
