import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.ImageIcon;

public class VideoSocketCommunication extends SocketCommunication {

    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    VideoSocketCommunication(CommunicationRole type) {
        super(type);
    }

    public void sendImage(ImageIcon imageIcon) {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageIcon getImage() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            ImageIcon imageIcon = (ImageIcon) in.readObject();
            return imageIcon;

        } catch (Exception e) {

        }
        return new ImageIcon();
    }
}
