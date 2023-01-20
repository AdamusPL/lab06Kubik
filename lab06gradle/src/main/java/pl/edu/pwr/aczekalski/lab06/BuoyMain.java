package pl.edu.pwr.aczekalski.lab06;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BuoyMain {
    private Socket clientWorld;
    private Socket clientCentral;
    private PrintWriter outWorld;
    private PrintWriter outCentral;
    private BufferedReader inWorld;
    private BufferedReader inCentral;

    public void startConnectionWorld(String ip, int port) throws IOException {
        clientWorld = new Socket(ip, port);
        outWorld = new PrintWriter(clientWorld.getOutputStream(), true);
        inWorld = new BufferedReader(new InputStreamReader(clientWorld.getInputStream()));
    }

    public void startConnectionCentral(String ip, int port) throws IOException {
        clientCentral = new Socket(ip, port);
        outCentral = new PrintWriter(clientCentral.getOutputStream(), true);
        inCentral = new BufferedReader(new InputStreamReader(clientCentral.getInputStream()));
    }

    public void sendRequestWorld(String request) throws IOException {
        outWorld.println(request);
    }

    public void sendRequestCentral(String request) throws IOException {
        outCentral.println(request);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        BuoyMain client = new BuoyMain();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                client.startT();
            }
        });
    }

    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                startConnectionWorld("127.0.0.1",49152); //połączenie boi do świata
                startConnectionCentral("127.0.0.1",49153); //połączenie boi do centrali
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                sendRequestWorld("BUOY");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String info=null;
            while (true){
                try {
                    info = inWorld.readLine();
                    if(info!=null){
                        if("WAVE".equals(info)){
                            System.out.println("Fala");
                            outCentral.println(info);
                        }
                    }
                    else System.out.println();
                    System.out.println(info);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });

    public void startT(){
        t.start();
    }


}
