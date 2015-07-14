package com.rsi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanrafael.perez on 14/07/2015.
 */
public class PruebasRefThread {

    public static void main(String[] args)  {

        List<MiThread> aT = new ArrayList<>();

        aT.add(new MiThread());
        aT.add(new MiThread());
        aT.add(new MiThread());
        aT.add(new MiThread());
        aT.add(new MiThread());
        aT.add(new MiThread());
        aT.add(new MiThread());

        for (MiThread oT: aT){

            System.out.println(oT.getId() + " --> " + oT.isAlive());
            oT.start();

        }

        for (MiThread oT: aT){

            // Los paramos todos
            oT.setExecute(false);

        }

        while (aT.get(0).isAlive() ||aT.get(1).isAlive() ||aT.get(2).isAlive() ||aT.get(3).isAlive() ||aT.get(4).isAlive() ||aT.get(5).isAlive()){
            System.out.println("----------------------------------");
            System.out.println(aT.get(0).getId() + " --> " + aT.get(0).isAlive());
            System.out.println(aT.get(1).getId() + " --> " + aT.get(1).isAlive());
            System.out.println(aT.get(2).getId() + " --> " + aT.get(2).isAlive());
            System.out.println(aT.get(3).getId() + " --> " + aT.get(3).isAlive());
            System.out.println(aT.get(4).getId() + " --> " + aT.get(4).isAlive());
            System.out.println(aT.get(5).getId() + " --> " + aT.get(5).isAlive());

        }

        System.out.println("Última ----------------------------------");

        for (MiThread oT: aT){

            System.out.println(oT.getId() + " --> " + oT.isAlive());
            // Los paramos todos
            oT.setExecute(false);

        }

    }

    public static class MiThread extends Thread{

        private static boolean execute = true;

        public void run(){

            while (execute){

            }
        }

        public void setExecute(boolean execute){
            this.execute = execute;
        }

    }
}
