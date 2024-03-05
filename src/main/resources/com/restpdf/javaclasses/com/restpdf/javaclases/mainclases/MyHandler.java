package com.restpdf.javaclases.mainclases;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.text.StringEscapeUtils;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Custom handler for handling HTTP requests.
 */
class MyHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestParamValue=null;

        if("GET".equals(exchange.getRequestMethod())) {
            requestParamValue = handleGetRequest(exchange);
//        } else if ("POST".equals(exchange)) {
//            requestParamValue = handlePostRequest(exchange);
        }
        handleResponse(exchange,requestParamValue);
    }
    private String handleGetRequest(HttpExchange httpExchange) {

        return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
    }
    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {

        OutputStream outputStream = httpExchange.getResponseBody();

        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<html>").
        append("<body>").
        append("<h1>").
        append("Hello ")
                .append(requestParamValue)
                .append("</h1>")
                .append("</body>")
                .append("</html>");
        String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());
 //        this line is a must

        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

//    @Override
//    public void handle(HttpExchange exchange) throws IOException {
//        String response = "Hello, World!";
//        exchange.sendResponseHeaders(200, response.length());
//        exchange.getResponseBody().write(response.getBytes());
//        exchange.getResponseBody().close();
//    }

}
