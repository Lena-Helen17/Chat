package com.moshkova.elena;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnection {
    private final Socket socket;
    private final Thread xThread;
    private String nickName;
    private final TCPConnectionAbcerver eventAbcerver;
    private final BufferedReader in;
    private  final BufferedWriter out;

    public  TCPConnection (TCPConnectionAbcerver eventAbcerver, String adres, int port) throws IOException {
        this(eventAbcerver, new Socket(adres, port));
    }

    public TCPConnection (TCPConnectionAbcerver eventAbcerver, Socket socket) throws IOException {
        this.eventAbcerver = eventAbcerver;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        xThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventAbcerver.onConnectionReady(TCPConnection.this);
                    while (!xThread.isInterrupted()) {
                        String text = in.readLine();
                        eventAbcerver.onReceiveString(TCPConnection.this, text);
                    }
                } catch (IOException e) {
                    eventAbcerver.onExseption(TCPConnection.this, e);
                } finally {
                    eventAbcerver.onDisconnect(TCPConnection.this);
                }
            }
        });
        xThread.start();
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public synchronized void sendString (String line) {
        try {
            out.write(line + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventAbcerver.onExseption(TCPConnection.this, e);
            disconnect();
        }

    }
    public synchronized void disconnect() {
        xThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventAbcerver.onExseption(TCPConnection.this, e);
        }

    }

    @Override
    public String toString() {
        return TCPConnection.this.nickName + ": (" + socket.getInetAddress() + ")"+ "\n";
    }
}
