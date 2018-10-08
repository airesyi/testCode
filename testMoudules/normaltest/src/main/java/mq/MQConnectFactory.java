package mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.jms.connection.CachingConnectionFactory;

import java.net.URL;

public class MQConnectFactory {
    private static PooledConnectionFactory pooledConnectionFactory = null;

    private static String mqurl = "";

    static class Load {

    }

    public static PooledConnectionFactory getPooledConnectionFactory() {
        if (pooledConnectionFactory == null) {
            pooledConnectionFactory = new PooledConnectionFactory();
            pooledConnectionFactory.setMaxConnections(100);

            ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
            activeMQConnectionFactory.setBrokerURL("tcp://127.0.0.1:61616");
            activeMQConnectionFactory.setUserName("admin");
            activeMQConnectionFactory.setPassword("admin");
            activeMQConnectionFactory.setUseAsyncSend(true);

            pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
            setPooledConnectionFactory(pooledConnectionFactory);
            return pooledConnectionFactory;
        }
        return pooledConnectionFactory;
    }

    public static CachingConnectionFactory getCachingConnectionFactory() {
        PooledConnectionFactory pooledConnectionFactory = getPooledConnectionFactory();
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();

        cachingConnectionFactory.setTargetConnectionFactory(pooledConnectionFactory);
        cachingConnectionFactory.setSessionCacheSize(100);
        cachingConnectionFactory.setClientId("test4test");

        return cachingConnectionFactory;
    }


    public static String getMqurl() {
        return mqurl;
    }

    public static void setMqurl(String mqurl) {
        MQConnectFactory.mqurl = mqurl;
    }

    public static void setPooledConnectionFactory(PooledConnectionFactory pooledConnectionFactory) {
        MQConnectFactory.pooledConnectionFactory = pooledConnectionFactory;
    }
}
