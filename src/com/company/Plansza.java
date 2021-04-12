package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class Plansza {
    int punkty=0;
    private int w = 10;
    private int h = 10;
    private int[] plansza = new int[100];
    public int PozycjaGracza;
    private int StaraPozycjaGracza;
    public int PoczatkowaPozycjaPotworka;
    public int KoncowaPozycjaPotworka;
    public int skok;
    public int PoczatkowySkok;
    public int StaraPozycjaPotworka;
    public int ObecnaPozycjaPotworka;
    private int Meta ;
    public boolean wygrana=false;
    public boolean smierc=false;
    public boolean klucz=false;
    public int PozycjaKlucza;
    public int PozycjaDrzwi;
    private int[] Kolumny ;
    private int[] Punkty ;
    private String[] nazwy = {"podloga.jpg", //kod 0
            "kolumna.jpg", //kod 1
            "miejsce.jpg", //kod 2
            "gracz.jpg", //kod 3
            "graczmc.jpg", //kod 4
            "punkt.jpg",//kod 5
            "potworek.jpg", //kod 6
            "smierc.jpg",//kod 7
            "klucz.jpg", //kod 8
            "drzwi.jpg" //kod 9
    }; //tablica z nazwami obrazków
    private Image obrazy[]=new Image[nazwy.length];
    public Plansza(String lvl) //konstruktor klasy Plansza
    {
        for(int i=0;i<nazwy.length;i++) {
            obrazy[i] = Toolkit.getDefaultToolkit().getImage(nazwy[i]);
        }
        try{wczytaj(lvl);}
        catch (Exception e){}
        UstawPlansze();

    }
    public void UstawPlansze()
    {
        for (int i = 0; i < (w * h); i++)
        {
            plansza[i] = 0; //
        }
        plansza[Meta] = 2; // 2 - kod mety
        plansza[PozycjaDrzwi]=9;
        plansza[PozycjaKlucza]=8;
        plansza[PozycjaGracza] = 3;
        plansza[PoczatkowaPozycjaPotworka]=6;
        for (int i = 0; i < Kolumny.length; i++){
        plansza[Kolumny[i]]=1;
        }
        for (int i = 0; i < Punkty.length; i++){
            plansza[Punkty[i]]=5;
        }
    }
    public void ZmienPlansze() //zmianie ulega tylko pozycja gracza
    {
        if(smierc==false) {
            plansza[StaraPozycjaPotworka] = 0;
            plansza[ObecnaPozycjaPotworka] = 6;
            if (plansza[PozycjaGracza] == 1||plansza[PozycjaGracza]==9)
                PozycjaGracza = StaraPozycjaGracza;
                // jeśli pozycja gracza różni się od starej pozycji gracza –na starej// pozycji rysujemy podłogę
            else if (plansza[PozycjaGracza] == 0) {
                plansza[StaraPozycjaGracza] = 0;
                plansza[PozycjaGracza] = 3;
            }
            // jeśli pozycja gracza pokrywa się z metą –w miejscu mety rysujemy
            // obrazek z graczem na mecie
            else if (plansza[PozycjaGracza] == 5) {
                plansza[StaraPozycjaGracza] = 0;
                plansza[PozycjaGracza] = 3;
                punkty = punkty + 1;
                Zrodelko z2 = new Zrodelko("Drugi");
                z2.start();
                Muzyka.grajPan(new File("getpoint.wav"));

            } else if (plansza[PozycjaGracza] == 2) {
                plansza[StaraPozycjaGracza] = 0;
                plansza[PozycjaGracza] = 4;
                wygrana = true;
            }
            else if (plansza[PozycjaGracza]==8)
            {
                plansza[StaraPozycjaGracza] = 0;
                plansza[PozycjaGracza] = 3;
                plansza[PozycjaDrzwi]=0;
            }
        }
        if(smierc==true)
        {
            plansza[StaraPozycjaGracza]=0;
            plansza[PozycjaGracza]=7;
        }


    }
    public void RysujPlansze(Graphics2D g)
    {

            ZmienPlansze();


            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {

                    g.drawImage(obrazy[plansza[i + w * j]], 75 + i * 30, 75 + j * 30, null);


                }
            }


    }
    public void PrzesunLudka(int c)
    {

        switch (c)
        {
            case 38:
            {
                StaraPozycjaGracza = PozycjaGracza;
                if(PozycjaGracza - 10 >= 0)
                    PozycjaGracza -= 10;
                break;
            }
            case 39:
            {
                StaraPozycjaGracza = PozycjaGracza;
                if((PozycjaGracza + 1) % 10 != 0 && ((PozycjaGracza + 1) <=99))
                    PozycjaGracza += 1;
                break;
            }

            case 40:
            {
                StaraPozycjaGracza = PozycjaGracza;
                if (PozycjaGracza + 10 <= 99)
                    PozycjaGracza += 10;
                break;
            }
            case 37:
            {
                StaraPozycjaGracza = PozycjaGracza;
                if((PozycjaGracza - 1) % 10 != 9 && ((PozycjaGracza - 1) >= 0))
                    PozycjaGracza -= 1;
                break;
            }
        }

    }
    public void PrzestawPotworka() {
        if(PoczatkowySkok>0) {
            this.StaraPozycjaPotworka = this.ObecnaPozycjaPotworka;
            if (this.ObecnaPozycjaPotworka + skok > this.KoncowaPozycjaPotworka) skok = -skok;
            if (this.ObecnaPozycjaPotworka + skok < this.PoczatkowaPozycjaPotworka) skok = -skok;
            this.ObecnaPozycjaPotworka = this.ObecnaPozycjaPotworka + skok;
        }
        else{
            this.StaraPozycjaPotworka = this.ObecnaPozycjaPotworka;
            if (this.ObecnaPozycjaPotworka + skok < this.KoncowaPozycjaPotworka) skok = -skok;
            if (this.ObecnaPozycjaPotworka + skok > this.PoczatkowaPozycjaPotworka) skok = -skok;
            this.ObecnaPozycjaPotworka = this.ObecnaPozycjaPotworka + skok;
        }
        }
    public void wczytaj(String lvl)
    {
            try{
            BufferedReader reader;
            lvl="lvl/"+lvl;
            reader =new BufferedReader(new FileReader(lvl));
            String linia =reader.readLine();

                String[] dane=linia.split(",");
                PozycjaGracza=Integer.parseInt(dane[0]);
                StaraPozycjaGracza=PozycjaGracza;
                linia=reader.readLine();
                String[] dane1=linia.split(",");
                PoczatkowaPozycjaPotworka=Integer.parseInt(dane1[0]);
                ObecnaPozycjaPotworka=PoczatkowaPozycjaPotworka;
                skok=Integer.parseInt(dane1[1]);
                PoczatkowySkok=skok;
                KoncowaPozycjaPotworka=Integer.parseInt(dane1[2]);

                linia=reader.readLine();
                String[] dane2=linia.split(",");
                Meta=Integer.parseInt(dane2[0]);
                this.PozycjaKlucza=Integer.parseInt(dane2[1]);
                PozycjaDrzwi=Integer.parseInt(dane2[2]);

                linia=reader.readLine();
                String[] dane3=linia.split(",");
                Kolumny=new int[dane3.length];
                for(int i=0;i<Kolumny.length;i++) Kolumny[i]=Integer.parseInt(dane3[i]);

                linia=reader.readLine();
                String[] dane4=linia.split(",");
                Punkty=new int[dane4.length];
                for(int i=0;i<Punkty.length;i++) {
                    Punkty[i] = Integer.parseInt(dane4[i]);
                }
            }
            catch (Exception e){}


    }
}

