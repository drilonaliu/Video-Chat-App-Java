import javax.swing.ImageIcon;
import com.github.sarxos.webcam.Webcam;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoCallWindow extends javax.swing.JFrame {

    private javax.swing.JLabel friendCamera;
    private javax.swing.JLabel myCamera;
    private VideoSocketCommunication chatUser;
    private Webcam cam;
    private javax.swing.JPanel jPanel1;

    public VideoCallWindow(VideoSocketCommunication chatUser) {
        initComponents();
        this.chatUser = chatUser;
        cam = Webcam.getDefault();

        Dimension resolution = new Dimension(800, 600); // e.g. 800 x 600

        cam.setCustomViewSizes(new Dimension[] { resolution });
        cam.setViewSize(resolution);
        cam.open();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                chatUser.close();
                dispose();
            }
        });
        appearMyCamera();
        // appearFriendCamera();
    }

    public void appearMyCamera() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ImageIcon icon;
                BufferedImage bufferedImage;
                while (true) {
                    bufferedImage = cam.getImage();
                    icon = new ImageIcon(bufferedImage);
                    myCamera.setIcon(icon);
                    chatUser.sendImage(icon);
                }
            }
        });

        thread.start();
    }

    public void appearFriendCamera() {
        Thread thread = new Thread(new Runnable() {
            ImageIcon icon;

            @Override
            public void run() {
                while (true) {
                    icon = chatUser.getImage();
                    friendCamera.setIcon(icon);
                }
            }
        });

        thread.start();
    }

    public void open() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VideoCallWindow.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VideoCallWindow.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VideoCallWindow.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VideoCallWindow.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        try {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    setVisible(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        friendCamera = new javax.swing.JLabel();
        myCamera = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(690, 800));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(53, 56, 64));
        jPanel1.setForeground(new java.awt.Color(53, 56, 64));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 800));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(myCamera, javax.swing.GroupLayout.DEFAULT_SIZE, 788,
                                                Short.MAX_VALUE)
                                        .addComponent(friendCamera, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(friendCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 353,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(myCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 328,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, Short.MAX_VALUE)
                                .addContainerGap()));

        pack();
    }//
}
