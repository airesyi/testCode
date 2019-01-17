package io.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * auth: shi yi
 * create date: 2019/1/16
 */
public interface TCPProtocol {
    void handleAccept(SelectionKey key) throws IOException;

    void handleRead(SelectionKey key) throws IOException;

    void handleWrite(SelectionKey key) throws IOException;

}
