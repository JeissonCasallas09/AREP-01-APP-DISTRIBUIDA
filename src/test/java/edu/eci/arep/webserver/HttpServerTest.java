package edu.eci.arep.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HttpServerTest {

    private static Thread serverThread;

    @BeforeAll
    public static void startServer() {
        serverThread = new Thread(() -> {
            try {
                HttpServer.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void stopServer() {
        serverThread.interrupt();
    }

    @Test
    public void testServeStaticFile() throws IOException {
        try (Socket socket = new Socket("localhost", 35000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("GET /index.html HTTP/1.1");
            out.println("Host: localhost");
            out.println();
            out.flush();

            String responseLine;
            StringBuilder response = new StringBuilder();
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine).append("\n");
                if (responseLine.isEmpty()) break;
            }

            assertTrue(response.toString().contains("HTTP/1.1 200 OK"));
            assertTrue(response.toString().contains("Content-Type: text/html"));
        }
    }

    @Test
    public void testHelloRestService() throws IOException {
        try (Socket socket = new Socket("localhost", 35000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("GET /app/hello?name=TestUser HTTP/1.1");
            out.println("Host: localhost");
            out.println();
            out.flush();

            String responseLine;
            StringBuilder response = new StringBuilder();
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine).append("\n");
                if (responseLine.isEmpty()) break;
            }

            assertTrue(response.toString().contains("HTTP/1.1 200 OK"));
            assertTrue(response.toString().contains("Content-Type: application/json"));
        }
    }

    @Test
    public void testFileNotFound() throws IOException {
        try (Socket socket = new Socket("localhost", 35000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("GET /nonexistent.html HTTP/1.1");
            out.println("Host: localhost");
            out.println();
            out.flush();

            String responseLine;
            StringBuilder response = new StringBuilder();
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine).append("\n");
                if (responseLine.isEmpty()) break;
            }

            assertTrue(response.toString().contains("HTTP/1.1 500 Internal Server Error"));
        }
    }

    @Test
    public void testGetContentTypeHtml() {
        String contentType = HttpServer.getContentType("index.html");
        assertEquals("text/html", contentType);
    }

    @Test
    public void testGetContentTypeCss() {
        String contentType = HttpServer.getContentType("styles.css");
        assertEquals("text/css", contentType);
    }

    @Test
    public void testGetContentTypeJs() {

        String contentType = HttpServer.getContentType("script.js");
        assertEquals("application/javascript", contentType);
    }

    @Test
    public void testGetContentTypePng() {
        String contentType = HttpServer.getContentType("image.png");
        assertEquals("image/png", contentType);
    }
}
