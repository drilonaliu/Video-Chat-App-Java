/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author drilo
 */
public class ChatWindow extends javax.swing.JFrame implements KeyListener {
    private JButton cameraButton;
    private JTextArea chatArea;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTextField jTextField1;
    private JButton microphoneButton;
    private JButton sendMessageButton;
    private JTextField writingArea;
    private boolean buttonPressed = false;
    private ChatSocketCommunication chatUser;

    /**
     * Creates new form NewJFrame
     */
    public ChatWindow(ChatSocketCommunication chatUser) {
        initComponents();
        this.chatUser = chatUser;
        getNewMessages();
    }

    // Send message
    private void sendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String text = writingArea.getText();

        String author = "";

        if (chatUser.getType() == CommunicationRole.SERVER) {
            author = "Server";
        } else {
            author = "Client";
        }
        Message message = new Message(text, Calendar.getInstance(), author);

        chatUser.sendMessage(message);
        chatArea.append("\n\n" + message.toString());
    }

    // Get the new messages
    private void getNewMessages() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Message newMessage = chatUser.getNewMessages();
                        chatArea.append("\n\n" + newMessage.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        chatUser.close();
                        dispose();
                    }

                }

            }
        });
        thread.start();
    }

    // Enable the microphone
    private void microphoneButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int port = 1234;
        AudioSocketCommunication user = new AudioSocketCommunication(chatUser.getType());
        user.startConnectionBasedOnType(port);
        user.sendAudio();
        user.listenAudio();
    }

    private boolean inConnection = false;

    // Start video call
    private void cameraButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("belo i iz pressed");
        buttonPressed = !buttonPressed;
        WindowValues.cameraButtonPressed = buttonPressed;
        int port = 3000;
        if (!inConnection) {

            VideoSocketCommunication user = new VideoSocketCommunication(chatUser.getType());
            user.startConnectionBasedOnType(port);
            VideoCallWindow window = new VideoCallWindow(user);
            window.open();

            if (chatUser.getType() == CommunicationRole.SERVER) {
                // try {
                //     System.out.println("Creating a new Server on port " + port);
                //     ServerSocket server = new ServerSocket(port);
                //     Socket socket = server.accept();
                //     System.out.println("A client has been accepted!");
                //     VideoSocketCommunication chatUser = new VideoSocketCommunication(socket, CommunicationRole.SERVER);
                //     VideoCallWindow window = new VideoCallWindow(chatUser);
                //     window.open();
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }

            } else {
                // try {
                //     String host = chatUser.getSocket().getInetAddress().getHostName();
                //     System.out.println("Creating a new user on  " + port + "host " + host);
                //     Socket socket = new Socket(host, port);
                //     VideoSocketCommunication chatUser = new VideoSocketCommunication(socket, CommunicationRole.CLIENT);
                //     VideoCallWindow window = new VideoCallWindow(chatUser);
                //     inConnection = true;
                //     window.open();
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }
                inConnection = true;
            }
        }

    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        writingArea = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        sendMessageButton = new javax.swing.JButton();
        cameraButton = new javax.swing.JButton();
        microphoneButton = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 51, 0));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(53, 56, 64));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        writingArea.setBackground(new java.awt.Color(102, 102, 102));
        writingArea.setForeground(new java.awt.Color(255, 255, 255));
        writingArea.setBorder(null);
        writingArea.setHighlighter(null);
        writingArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                writingAreaActionPerformed(evt);
            }
        });

        chatArea.setBackground(new java.awt.Color(227, 227, 227));
        chatArea.setColumns(20);
        chatArea.setRows(5);
        chatArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setViewportView(chatArea);

        sendMessageButton.setBackground(new java.awt.Color(53, 56, 64));
        sendMessageButton.setForeground(new java.awt.Color(53, 56, 64));
        sendMessageButton.setIcon(new javax.swing.ImageIcon(("ChatApp/icons/send-mail (1).png"))); // NOI18N
        sendMessageButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        sendMessageButton.setContentAreaFilled(false);
        sendMessageButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageButtonActionPerformed(evt);
            }
        });

        cameraButton.setBackground(new java.awt.Color(53, 56, 64));
        cameraButton.setIcon(new javax.swing.ImageIcon(("ChatApp/icons/camera (2).png"))); // NOI18N
        cameraButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cameraButton.setContentAreaFilled(false);
        cameraButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cameraButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraButtonActionPerformed(evt);
            }
        });

        microphoneButton.setBackground(new java.awt.Color(53, 56, 64));
        microphoneButton.setForeground(new java.awt.Color(53, 56, 64));
        microphoneButton.setIcon(new javax.swing.ImageIcon(("ChatApp/icons/microphone.png"))); // NOI18N
        microphoneButton.setBorder(null);
        microphoneButton.setBorderPainted(false);
        microphoneButton.setContentAreaFilled(false);
        microphoneButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        microphoneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                microphoneButtonActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                .createSequentialGroup()
                                                .addComponent(writingArea, javax.swing.GroupLayout.PREFERRED_SIZE, 427,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(sendMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 483,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cameraButton)
                                        .addComponent(microphoneButton))
                                .addContainerGap(99, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cameraButton)
                                                .addGap(86, 86, 86)
                                                .addComponent(microphoneButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                .createSequentialGroup()
                                                .addContainerGap(36, Short.MAX_VALUE)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(sendMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(writingArea, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(68, 68, 68)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));

        pack();

        chatArea.setEditable(false);
        addKeyListener(this);
        setFocusable(true);
        getRootPane().setDefaultButton(sendMessageButton);
    }// </editor-fold>

    private void writingAreaActionPerformed(java.awt.event.ActionEvent evt) {

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
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }

        try {
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    setVisible(true);
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            java.awt.event.ActionEvent dummyEvent = new java.awt.event.ActionEvent(sendMessageButton,
                    java.awt.event.ActionEvent.ACTION_PERFORMED, "");
            sendMessageButtonActionPerformed(dummyEvent);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
}

// Send Message
