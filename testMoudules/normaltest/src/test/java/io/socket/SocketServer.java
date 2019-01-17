package io.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * auth: shi yi
 * create date: 2019/1/15
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);

        System.out.println("waiting...");
        while (true) {
            String line = null;
            System.out.println("1");
            Socket socket = server.accept();
            System.out.println("2");

            InputStream is = socket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            PrintWriter os = new PrintWriter(socket.getOutputStream(), true);
            os.println("hello client");
            os.flush();

            reader.close();
            is.close();
            os.close();
            socket.close();
        }
    }
}
