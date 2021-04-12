package com.company;

public class Zrodelko implements Runnable {

    private Thread t;
    private String name;

    public Zrodelko(String name) {
        this.name = name;
        System.out.println("Tworze wlaśnie wątek: "+ name);
    }

    @Override
    public void run() {
        System.out.println("Wlasnie uruchamiam watek: "+ name);
        try{
            for(int i = 4; i > 0; i--){
                System.out.println("Wątek " + name + ": iteracja " + i);
                // Let the thread sleep for a while.
                Thread.sleep(50);
            }
        }catch (InterruptedException e) {
            System.out.println("Wątek " +  name + " interrupted.");
        }
        System.out.println("Wątek " +  name + " exiting.");

    }
    public void start () {
        System.out.println("Rozpoczynam " +  name );
        if (t == null) {
            t = new Thread (this, name);
            t.start ();
        }
    }
}