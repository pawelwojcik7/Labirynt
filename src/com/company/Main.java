package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Zrodelko z1 = new Zrodelko("Pierwszy");
        z1.start();
        JFrame frame = new JFrame("Okno");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Mapa mapa = new Mapa();
        frame.getContentPane().add(mapa);
        frame.setPreferredSize(new Dimension(450,450));
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);

    }
}