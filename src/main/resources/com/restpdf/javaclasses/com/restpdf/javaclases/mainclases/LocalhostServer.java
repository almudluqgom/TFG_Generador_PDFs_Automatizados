package com.restpdf.javaclases.mainclases;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class LocalhostServer {

    /**
     * Starts the localhost server on the specified port.
     *
     * @param port The port number on which the server should listen.
     * @throws IOException if an I/O error occurs while starting the server.
     */
//    public static void startServer(int port) throws IOException {
//        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
//        server.createContext("/", new MyHandler());
//        server.setExecutor(null);
//        server.start();
//        System.out.println("Server started on port " + port);
//    }

    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        server.createContext("/test", new  MyHandler());
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println("Server started on port 8001");
    }
    public static void main(String[] args) throws IOException {
        int port = 8080;
        //startServer(port);
        startServer();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationFrame mainFrame = new ApplicationFrame();
                mainFrame.setVisible(true);
            }
        });
    }
}
