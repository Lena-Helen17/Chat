package com.moshkova.elena;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;


public class Server extends JFrame implements TCPConnectionAbcerver{
    public static void main(String[] args) {
        new Server();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();
    private JTextArea listMessageText = new JTextArea(8,40);
    private JTextArea messageText = new JTextArea(8,40);

    public Server() {
        setTitle("Сервер");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 400));
        setResizable(false);

        JScrollPane scrollPane = new JScrollPane(listMessageText);
        add(scrollPane, BorderLayout.EAST);

        JScrollPane jScrollPane = new JScrollPane(messageText);
        add(jScrollPane);

        setVisible(true);


        try (ServerSocket serverSocket = new ServerSocket(7777)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                }catch (IOException e) {
                    System.out.println("Oшибка соединения: " + e);
                }
            }

        }catch (Exception e){
            throw  new RuntimeException(e);
        }

    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        messageText.setText(connections.toString());
        sendToAllConnection("Client connected: " + tcpConnection);
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String string) {
        sendToAllConnection(string);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        messageText.setText(connections.toString());
        sendToAllConnection("Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onExseption(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection исключение: " + e);

    }

    private void sendToAllConnection (String text) {
        listMessageText.append(text + "\n");
        final int x = connections.size();
        for (int i = 0; i < x; i++) {
            connections.get(i).sendString(text);
        }
    }
}
