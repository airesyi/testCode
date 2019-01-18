package io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * auth: shi yi
 * create date: 2019/1/16
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel clntChan = SocketChannel.open();
        clntChan.configureBlocking(false);
        if (!clntChan.connect(new InetSocketAddress("localhost", 10083))){
            //不断地轮询连接状态，直到完成连接
            while (!clntChan.finishConnect()){
                //在等待连接的时间里，可以执行其他任务，以充分发挥非阻塞IO的异步特性
                //这里为了演示该方法的使用，只是一直打印"."
                System.out.print(".");
            }
        }

        //为了与后面打印的"."区别开来，这里输出换行符
        System.out.print("\n");
        //分别实例化用来读写的缓冲区

        ByteBuffer writeBuf = ByteBuffer.wrap("send send send111".getBytes());
        ByteBuffer readBuf = ByteBuffer.allocate("send".getBytes().length-1);

        while (writeBuf.hasRemaining()) {
            //如果用来向通道中写数据的缓冲区中还有剩余的字节，则继续将数据写入信道
            clntChan.write(writeBuf);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StringBuffer stringBuffer=new StringBuffer();
        //如果read（）接收到-1，表明服务端关闭，抛出异常
        System.out.println("connect:"+clntChan.isConnected());

        while ((clntChan.read(readBuf)) >0){
            readBuf.flip();
            stringBuffer.append(new String(readBuf.array(),0,readBuf.limit()));
            readBuf.clear();
        }
        System.out.println("block:"+clntChan.isBlocking());
        //打印出接收到的数据
        System.out.println("Client Received: " +  stringBuffer.toString());
        System.out.println("finish connect:"+clntChan.finishConnect());
        //关闭信道
        clntChan.close();
    }
}
