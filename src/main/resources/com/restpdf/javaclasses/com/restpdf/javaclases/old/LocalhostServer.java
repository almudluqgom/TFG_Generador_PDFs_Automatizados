package com.restpdf.javaclases.old;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class LocalhostServer {

    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        server.createContext("/test", new MyHandler());
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println("Server started on port 8001");
    }
//    public static void main(String[] args) throws IOException {
//        int port = 8080;
//        //startServer(port);
//        startServer();
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                BDFrame mainFrame = null;
//                try {
//                    mainFrame = new BDFrame();
//                } catch (SQLException | ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//                mainFrame.setVisible(true);
//            }
//        });
//    }
}
