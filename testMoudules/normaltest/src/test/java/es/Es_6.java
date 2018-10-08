package es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * auth: shi yi
 * create date: 2018/9/18
 */
public class Es_6 {
    public static void getClient() throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("host1"), 9300))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("host2"), 9300));

    }
}
