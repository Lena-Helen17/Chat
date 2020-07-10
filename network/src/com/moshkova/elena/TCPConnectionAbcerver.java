package com.moshkova.elena;

public interface TCPConnectionAbcerver {
    void onConnectionReady (TCPConnection tcpConnection);
    void onReceiveString (TCPConnection tcpConnection, String string);
    void onDisconnect (TCPConnection tcpConnection);
    void onExseption (TCPConnection tcpConnection, Exception e);
}
