import java.io.*;
import javax.sound.sampled.*;

public class AudioSocketCommunication extends SocketCommunication {

    private TargetDataLine microphone;
    private SourceDataLine speakers;
    private AudioFormat format = getAudioFormat();
    private DataLine.Info infoSend = new DataLine.Info(TargetDataLine.class, format);
    private DataLine.Info infoRecieve = new DataLine.Info(SourceDataLine.class, format);
    private InputStream inputStream;
    private OutputStream outputStream;

    AudioSocketCommunication(CommunicationRole type) {
        super(type);
        initVariables();
    }

    private void initVariables() {
        try {
            microphone = (TargetDataLine) AudioSystem.getLine(infoSend);
            speakers = (SourceDataLine) AudioSystem.getLine(infoRecieve);
            speakers.open(format);
            speakers.start();
            microphone.open(format);
            microphone.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAudio() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (Exception e) {
            // TODO: handle exception
        }
        Thread sendAudioThread = new Thread(new Runnable() {
            byte[] buffer = new byte[4096];
            int bytesRead;

            @Override
            public void run() {
                while ((bytesRead = microphone.read(buffer, 0, buffer.length)) != -1) {
                    try {
                        outputStream.write(buffer, 0, bytesRead);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        sendAudioThread.start();
    }

    public void listenAudio() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (Exception e) {
            // TODO: handle exception
        }

        Thread listenAudioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buffer = new byte[4096];
                int bytesRead;
                // Continuously receive audio data from the server and play it through the
                // speakers
                try {
                    while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                        speakers.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        listenAudioThread.start();
    }

    // Cleanup
    public void clear() {
        microphone.stop();
        microphone.close();
        speakers.drain();
        speakers.stop();
        speakers.close();
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

}
