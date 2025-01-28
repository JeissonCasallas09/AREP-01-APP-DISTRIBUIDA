package edu.eci.arep.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpServer {
    public static void main(String[] args) throws IOException, URISyntaxException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;

            Boolean isFirstLine = true;
            String file = "";

            while ((inputLine = in.readLine()) != null) {
                if (isFirstLine) {
                    file = inputLine.split(" ")[1];
                    isFirstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            URI resourceuri = new URI(file);
            System.out.println("URI: " + resourceuri);

            if (resourceuri.getPath().startsWith("/app/hello")) {
                outputLine = helloRestService(resourceuri.getPath(), resourceuri.getQuery());
                out.println(outputLine);

            } else {
                String filePath = "src\\main\\java\\edu\\eci\\arep\\webserver\\static" + resourceuri.getPath();
                if (filePath.endsWith("/")) {
                    filePath += "index.html";
                }
                serveFile(out, filePath, getContentType(filePath));
            }
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();

    }

    private static void serveFile(PrintWriter out, String filePath, String contentType) {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: " + contentType);
            out.println();
            out.write(new String(fileContent));
        } catch (IOException e) {
            out.println("HTTP/1.1 500 Internal Server Error\r\n\r\n");
            out.println("<h1>500 Internal Server Error</h1>");
        }
    }

    public static String getContentType(String file) {
        if (file.endsWith(".html"))
            return "text/html";
        if (file.endsWith(".css"))
            return "text/css";
        if (file.endsWith(".js"))
            return "application/javascript";
        if (file.endsWith(".png"))
            return "image/png";
        if (file.endsWith(".jpg") || file.endsWith(".jpeg"))
            return "image/jpeg";
        if (file.endsWith(".gif"))
            return "image/gif";
        return "text/plain";
    }

    private static String helloRestService(String path, String query) {
        System.out.println("Query: " + query);
        query = query.substring(5);
        String response = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + "{\"name\":" + "\"" + query + "\"" + ")";
        return (response);
    }
}