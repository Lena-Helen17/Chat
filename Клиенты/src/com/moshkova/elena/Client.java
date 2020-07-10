package com.moshkova.elena;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client extends JFrame implements TCPConnectionAbcerver{

        //private static String IP_ADDR = "localhost";
        private static String IP_ADDR = null;
        private static final int PORT = 7777;
        private static  String nickName = null;
        private static final int WIDTH = 600;
        private static final int HEIGHT = 400;


        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Client();
                }
            });
        }

        private final JTextArea log = new JTextArea();
        private TCPConnection connection;

        public Client() {
            setTitle("Клиентский чат");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(WIDTH, HEIGHT);
            setLocationRelativeTo(null);

            log.setEnabled(false);
            log.setLineWrap(true);
            add(log);

            JPanel panelMessage = new JPanel();

            JTextArea messageText = new JTextArea(8, 40);
            JScrollPane jScrollPane = new JScrollPane(messageText);
            JButton sendButton = new JButton("Send");

            panelMessage.add(jScrollPane);
            panelMessage.add(sendButton, BorderLayout.WEST);

            add(panelMessage, BorderLayout.SOUTH);

            Login_in login = new Login_in();
            login.setModal(true);
            login.setVisible(true);

            setLocationRelativeTo(login);
                nickName = login.nick;
                IP_ADDR = login.server;


            setVisible(true);
            try {
                connection = new TCPConnection(this, IP_ADDR, PORT);
                connection.setNickName(nickName);
                System.out.println(connection.toString());
            } catch (IOException e) {
                printMsg("Нет соединения: " + e);
            }

            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String msg = messageText.getText();
                    if (msg.equals("")) return;
                    messageText.setText(null);
                    String  data = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                    connection.sendString(data + ", " + nickName + ": \n" + msg);
                }
            });
        }

            @Override
            public void onConnectionReady (TCPConnection tcpConnection){
                printMsg("Cоединение готово...");
            }

            @Override
            public void onReceiveString (TCPConnection tcpConnection, String string){
                printMsg(string);
            }

            @Override
            public void onDisconnect (TCPConnection tcpConnection){
                printMsg("Cоединение закрыто...");
            }

            @Override
            public void onExseption (TCPConnection tcpConnection, Exception e){
                printMsg("Нет соединения: " + e);
            }

            private synchronized void printMsg (String msg){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        log.append(msg + "\n");
                        log.setCaretPosition(log.getDocument().getLength());
                    }
                });
            }
        }


