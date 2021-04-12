package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class  Mapa extends JPanel implements ActionListener, KeyListener {

    JPanel panel;
    JButton nowagra;
    JLabel punkty;
    JLabel czas;
    JButton ogrze;
    Plansza plansza;
    JButton nastepnylvl;
    JButton poprzednilvl;
    int klawisz;
    int d=0,f=1000,h=10;
    public int t=0;
    public int lvl=0;
    public int maxlvl;
    public File folder;
    public File[] listoffiles;



    public Mapa()
    {
        folder = new File("lvl");
        listoffiles =folder.listFiles();
        maxlvl=listoffiles.length-1;
        System.out.println(maxlvl);
        System.out.println(listoffiles[lvl].getName());
        System.out.println(listoffiles[maxlvl].getName());
        plansza=new Plansza(listoffiles[lvl].getName());
        panel=new JPanel();
        nowagra=new JButton("Nowa gra");
        add(nowagra);
        nowagra.addActionListener(this);
        ogrze=new JButton("O grze");
        ogrze.addActionListener(this);
        add(ogrze);
        poprzednilvl=new JButton("Poprzedni lvl");
        add(poprzednilvl);
        poprzednilvl.addActionListener(this);
        nastepnylvl=new JButton("Następny lvl");
        add(nastepnylvl);
        nastepnylvl.addActionListener(this);
        punkty=new JLabel();
        add(punkty);
        this.addKeyListener(this);
        this.setFocusable(true);
    }

@Override
    protected void paintComponent(Graphics g) {
    this.setFocusable(true);
    if (plansza.wygrana == true) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        plansza.RysujPlansze(g2);
        g2.setFont(new Font("Timesroman",Font.BOLD,24));
        g2.drawString("Wygrałeś ! Twój wynik to "+plansza.punkty,50,200);
        repaint();



    } else {
        if (t == 0) {
            d++;
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            plansza.RysujPlansze(g2);
            plansza.PrzesunLudka(klawisz);
            klawisz = 0;
            punkty.setText("Punkty: " + String.valueOf(plansza.punkty));
            if (d >= f) {
                plansza.PrzestawPotworka();
                plansza.RysujPlansze(g2);
                d = 0;
            }
            if (plansza.ObecnaPozycjaPotworka == plansza.PozycjaGracza) {
                t = 1;
                plansza.smierc = true;

            }
            repaint();
            nowagra.setFocusable(false);
            this.setFocusable(true);
        }
        if (t == 1) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            plansza.RysujPlansze(g2);
            klawisz = 0;
            punkty.setText("Punkty: " + String.valueOf(plansza.punkty));
            if (d >= f) {
             plansza.PrzestawPotworka();
            }
            g2.setFont(new Font("TimesRoman", Font.BOLD, 24));
            g2.drawString("Przegrałeś, Twój wynik to " + String.valueOf(plansza.punkty), 50, 200);
            repaint();
            nowagra.setFocusable(false);
            this.setFocusable(true);
        }
        if (t == 2) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            //g.clearRect(40,40,300,300);
            g2.drawString("W grze streujemy strzałkami." + "Musimy unikać potworka.", 50, 100);
            g2.drawString("Opcjonalnie możemy zbierać punkty.",50,125);
            g2.drawString("Plik jar nie działa, lecz działa gra z programu.",50,150);
            g2.drawString("Nie wymagane jest przejscie poprzedniego lvl do rozpoczęcia ",50,175);
            g2.drawString("nastepnego dla łatwosci testowania.",50,200);
            repaint();
            nowagra.setFocusable(false);
            ogrze.setFocusable(false);
            this.setFocusable(true);
        }


    }
}
@Override
    public void actionPerformed(ActionEvent actionevent)
{
    Object o = actionevent.getSource();
    if(o == nowagra)
    {
       Mapa nowamapa=new Mapa();
       this.plansza=nowamapa.plansza;

       t=0;


    }
    if(o==ogrze)
    {
        t=2;
        plansza.wygrana=false;
    }
    if(o==nastepnylvl)
    {
        if(lvl<maxlvl) {
            lvl=lvl+1;
            this.plansza=new Plansza(listoffiles[lvl].getName());
            this.plansza.smierc=false;
            t=0;
            this.nastepnylvl.setFocusable(false);
            this.panel.setFocusable(true);
        }
        else
        {
            lvl=0;
            this.plansza=new Plansza(listoffiles[lvl].getName());
            this.plansza.smierc=false;
            t=0;
            this.nastepnylvl.setFocusable(false);
            this.panel.setFocusable(true);
        }
    }
    if(o==poprzednilvl)
    {
        if(lvl>0) {
            lvl=lvl-1;
            this.plansza=new Plansza(listoffiles[lvl].getName());
            this.plansza.smierc=false;
            t=0;
            this.nastepnylvl.setFocusable(false);
            this.panel.setFocusable(true);
        }
        else
        {
            lvl=maxlvl;
            this.plansza=new Plansza(listoffiles[lvl].getName());
            this.plansza.smierc=false;
            t=0;
            this.nastepnylvl.setFocusable(false);
            this.panel.setFocusable(true);
        }
    }
}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        klawisz=e.getKeyCode();


    }
}
