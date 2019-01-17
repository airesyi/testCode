package io.socket;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * auth: shi yi
 * create date: 2019/1/15
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket();
        client.connect(new InetSocketAddress("localhost", 8080));
        PrintWriter os = new PrintWriter(client.getOutputStream(), true);

        os.println("hello!");
        os.flush();

        System.out.println("gogogo!");
        String line = null;
//        client.shutdownOutput();

        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String inputLine = null;
        while ((inputLine = reader.readLine()) != null) {
            System.out.println("from server:" + inputLine);
        }

        os.close();
        client.close();
    }
}
