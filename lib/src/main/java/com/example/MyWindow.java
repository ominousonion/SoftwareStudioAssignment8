package com.example;

import com.sun.java.util.jar.pack.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MyWindow extends JFrame{
    public JTextArea textarea;

    MyWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,200);
        this.setResizable(false);
        this.setLayout(null);
        this.textarea = new JTextArea();
        this.textarea.setEditable(false);
        this.textarea.setSize(300,200);
        this.add(textarea);
        this.setVisible(true);
    }

}
