package com.mycompany.parcial;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean bandera = true;
        Socket clientSocket = null;


        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibí: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }
        outputLine  = Response();
        out.println(outputLine);
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();


    }
    public static String Response(){
        String outputLine ="";
        outputLine  = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<title>Form Example</title>\n"
                + "<meta charset=\"UTF-8 \">\n"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>Form with GET</h1>\n"
                + "<form action=\"/hello\">\n"
                + "<label for=\"name\">Name:</label><br>"
                + "<input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>"
                + "<input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">"
                + "</form>\n"
                + "<div id=\"getrespmsg\"></div>"
                + "<script>\n"
                + " function loadGetMsg() {"
                + "let nameVar = document.getElementById(\"name\").value;"
                + " const xhttp = new XMLHttpRequest();"
                + " xhttp.onload = function() {"
                + " document.getElementById(\"getrespmsg\").innerHTML ="
                + " this.responseText;"
                +"}"
                +"xhttp.open(\"GET\", \"/hello?name=\"+ nameVar);"
                + "xhttp.send();"
                +"}"
                + "</script>"
                + "</body>\n"
                + "</html>\n";
        return outputLine;
    }
}