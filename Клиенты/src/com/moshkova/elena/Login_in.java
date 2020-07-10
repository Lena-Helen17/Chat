package com.moshkova.elena;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login_in extends JDialog {
    String server;
    String nick;

    public Login_in() {
        setTitle("Соединение с сервером");
        setSize(300, 300);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        JLabel login = new JLabel("Логин:");
        JLabel passvorld = new JLabel("Пароль:");
        JButton exitButten = new JButton("Exit");
        JButton registrashenButten = new JButton("Connect");
        JTextField loginText = new JTextField("localhost");
        JTextField passwordText = new JTextField("Ketrin");
        //JPasswordField passwordText = new JPasswordField(20);
        add(login, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        add(loginText, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        add(passvorld, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        add(passwordText, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        add(registrashenButten, new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        add(exitButten, new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(2, 2, 2, 2), 0, 0));
        pack();   //убирает лишнее пространств

        exitButten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
               // setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });

        registrashenButten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server = loginText.getText();
                nick = passwordText.getText();
                dispose();
            }
        });

    }
}
