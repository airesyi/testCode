import bean.Student;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Redis client base on jedis 根据继承类的不同,
 * jedis实例方式不用:JedisSimpleFactry/JedisPoolFactry/ShardedJedisPoolFactry
 *
 * @author xuxueli 2015-7-10 18:34:07
 * <p>
 * # for redis (sharded.jedis.address=host01:port,host02:port)
 * sharded.jedis.address=127.0.0.1:6379,127.0.0.1:6379,127.0.0.1:6379
 */
public class JedisUtil {

    private static final int DEFAULT_EXPIRE_TIME = 7200; // 默认过期时间,单位/秒, 60*60*2=2H, 两小时
    private static String address;

    public static void init(String address) {
        JedisUtil.address = address;
    }

    // ------------------------ ShardedJedisPool ------------------------
    /**
     * 方式01: Redis单节点 + Jedis单例 : Redis单节点压力过重, Jedis单例存在并发瓶颈 》》不可用于线上
     * new Jedis("127.0.0.1", 6379).get("cache_key");
     * 方式02: Redis单节点 + JedisPool单节点连接池 》》 Redis单节点压力过重，负载和容灾比较差
     * new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379, 10000).getResource().get("cache_key");
     * 方式03: Redis集群(通过client端集群,一致性哈希方式实现) + Jedis多节点连接池 》》Redis集群,负载和容灾较好, ShardedJedisPool一致性哈希分片,读写均匀，动态扩充
     * new ShardedJedisPool(new JedisPoolConfig(), new LinkedList<JedisShardInfo>());
     */

    private static ShardedJedisPool shardedJedisPool;
    private static ReentrantLock INSTANCE_INIT_LOCL = new ReentrantLock(false);

    /**
     * 获取ShardedJedis实例
     *
     * @return
     */
    private static ShardedJedis getInstance() {
        if (shardedJedisPool == null) {
            try {
                if (INSTANCE_INIT_LOCL.tryLock(2, TimeUnit.SECONDS)) {

                    try {

                        if (shardedJedisPool == null) {
                            // JedisPoolConfig
                            JedisPoolConfig config = new JedisPoolConfig();
                            config.setMaxTotal(200);            // 最大连接数, 默认8个
                            config.setMaxIdle(50);                // 最大空闲连接数, 默认8个
                            config.setMinIdle(8);                // 设置最小空闲数
                            config.setMaxWaitMillis(10000);        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
                            config.setTestOnBorrow(true);        // 在获取连接的时候检查有效性, 默认false
                            config.setTestOnReturn(true);       // 调用returnObject方法时，是否进行有效检查
                            config.setTestWhileIdle(true);        // Idle时进行连接扫描
                            config.setTimeBetweenEvictionRunsMillis(30000);    //表示idle object evitor两次扫描之间要sleep的毫秒数
                            config.setNumTestsPerEvictionRun(10);            //表示idle object evitor每次扫描的最多的对象数
                            config.setMinEvictableIdleTimeMillis(60000);    //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义


                            // JedisShardInfo List
                            List<JedisShardInfo> jedisShardInfos = new LinkedList<JedisShardInfo>();

                            String[] addressArr = address.split(",");
                            for (int i = 0; i < addressArr.length; i++) {
                                String[] addressInfo = addressArr[i].split(":");
                                String host = addressInfo[0];
                                int port = Integer.valueOf(addressInfo[1]);
                                JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port, 10000);
                                jedisShardInfo.setPassword("root");
                                jedisShardInfos.add(jedisShardInfo);
                            }
                            shardedJedisPool = new ShardedJedisPool(config, jedisShardInfos);
                        }

                    } finally {
                        INSTANCE_INIT_LOCL.unlock();
                    }
                }

            } catch (InterruptedException e) {
            }
        }

        if (shardedJedisPool == null) {
            throw new NullPointerException(">>>>>>>>>>> xxl-sso, JedisUtil.ShardedJedisPool is null.");
        }

        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        return shardedJedis;
    }

    // ------------------------ serialize and unserialize ------------------------

    /**
     * 将对象-->byte[] (由于jedis中不支持直接存储object所以转换成byte[]存入)
     *
     * @param object
     * @return
     */
    private static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    /**
     * 将byte[] -->Object
     *
     * @param bytes
     * @return
     */
    private static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    // ------------------------ jedis util ------------------------
    /**
     * 存储简单的字符串或者是Object 因为jedis没有分装直接存储Object的方法，所以在存储对象需斟酌下
     * 存储对象的字段是不是非常多而且是不是每个字段都用到，如果是的话那建议直接存储对象，
     * 否则建议用集合的方式存储，因为redis可以针对集合进行日常的操作很方便而且还可以节省空间
     */

    /**
     * Set String
     *
     * @param key
     * @param value
     * @param seconds 存活时间,单位/秒
     * @return
     */
    public static String setStringValue(String key, String value, int seconds) {
        String result = null;
        ShardedJedis client = getInstance();
        try {
            result = client.setex(key, seconds, value);
        } catch (Exception e) {
        } finally {
            client.close();
        }
        return result;
    }

    /**
     * Set String (默认存活时间, 2H)
     *
     * @param key
     * @param value
     * @return
     */
    public static String setStringValue(String key, String value) {
        return setStringValue(key, value, DEFAULT_EXPIRE_TIME);
    }

    /**
     * Set Object
     *
     * @param key
     * @param obj
     * @param seconds 存活时间,单位/秒
     */
    public static String setObjectValue(String key, Object obj, int seconds) {
        String result = null;
        ShardedJedis client = getInstance();
        try {
            result = client.setex(key.getBytes(), seconds, serialize(obj));
        } catch (Exception e) {
        } finally {
            client.close();
        }
        return result;
    }

    /**
     * Set Object (默认存活时间, 2H)
     *
     * @param key
     * @param obj
     * @return
     */
    public static String setObjectValue(String key, Object obj) {
        return setObjectValue(key, obj, DEFAULT_EXPIRE_TIME);
    }

    /**
     * Get String
     *
     * @param key
     * @return
     */
    public static String getStringValue(String key) {
        String value = null;
        ShardedJedis client = getInstance();
        try {
            value = client.get(key);
        } catch (Exception e) {
        } finally {
            client.close();
        }
        return value;
    }

    /**
     * Get Object
     *
     * @param key
     * @return
     */
    public static Object getObjectValue(String key) {
        Object obj = null;
        ShardedJedis client = getInstance();
        try {
            byte[] bytes = client.get(key.getBytes());
            if (bytes != null && bytes.length > 0) {
                obj = unserialize(bytes);
            }
        } catch (Exception e) {
        } finally {
            client.close();
        }
        return obj;
    }

    /**
     * Delete
     *
     * @param key
     * @return Integer reply, specifically:
     * an integer greater than 0 if one or more keys were removed
     * 0 if none of the specified key existed
     */
    public static Long del(String key) {
        Long result = null;
        ShardedJedis client = getInstance();
        try {
            result = client.del(key);
        } catch (Exception e) {
        } finally {
            client.close();
        }
        return result;
    }

    /**
     * incrBy	value值加i
     *
     * @param key
     * @param i
     * @return new value after incr
     */
    public static Long incrBy(String key, int i) {
        Long result = null;
        ShardedJedis client = getInstance();
        try {
            result = client.incrBy(key, i);
        } catch (Exception e) {
        } finally {
            client.close();
        }
        return result;
    }

    /**
     * exists
     *
     * @param key
     * @return Boolean reply, true if the key exists, otherwise false
     */
    public static boolean exists(String key) {
        Boolean result = null;
        ShardedJedis client = getInstance();
        try {
            result = client.exists(key);
        } catch (Exception e) {
        } finally {
            client.close();
        }
        return result;
    }

    /**
     * expire	重置存活时间
     *
     * @param key
     * @param seconds 存活时间,单位/秒
     * @return Integer reply, specifically:
     * 1: the timeout was set.
     * 0: the timeout was not set since the key already has an associated timeout (versions lt 2.1.3), or the key does not exist.
     */
    public static long expire(String key, int seconds) {
        Long result = null;
        ShardedJedis client = getInstance();
        try {
            result = client.expire(key, seconds);
        } catch (Exception e) {
        } finally {
            client.close();
        }
        return result;
    }

    /**
     * expireAt		设置存活截止时间
     *
     * @param key
     * @param unixTime 存活截止时间戳
     * @return
     */
    public static long expireAt(String key, long unixTime) {
        Long result = null;
        ShardedJedis client = getInstance();
        try {
            result = client.expireAt(key, unixTime);
        } catch (Exception e) {
        } finally {
            client.close();
        }
        return result;
    }

    public static void main(String[] args) {
        init("127.0.0.1:6379");
        ShardedJedis client = getInstance();
        Map<String, String> map = new Hashtable<String, String>();
        map.put("a", "123");
        map.put("b", "456");
        Collection<Jedis> jedisList = client.getAllShards();
        for (Jedis jedis : jedisList) {
            jedis.select(5);
        }

        client.hmset("key", map);
        client.hset("key", "d", "789");
        for (int i = 0; i < 10; i++) {
            System.out.println(client.hincrBy("key", "x", 1));
        }
//		System.out.println(client.hincrBy("key","1",1));
        System.out.println(client.hgetAll("321").isEmpty());
        client.expire("key", 5);
        client.close();
    }

    @Test
    public void test() {
        init("127.0.0.1:6379");
        ShardedJedis client = getInstance();

        Long time1 = System.currentTimeMillis();
        System.out.println(client.exists("aaaaa"));
        Long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);

        System.out.println(client.get("aaaaa"));

        Student student = null;
        Long time3 = System.currentTimeMillis();
        setObjectValue("aaaaa", student);
        Long time4 = System.currentTimeMillis();
        System.out.println(time4 - time3);

        Long time5 = System.currentTimeMillis();
        System.out.println(client.exists("aaaaa"));
        Long time6 = System.currentTimeMillis();
        System.out.println(time6 - time5);
    }

    @Test
    public void test1() {
        init("127.0.0.1:6379");
        ShardedJedis client = getInstance();
        Set<String> set = new HashSet<String>();
        set.add("1");
        set.add("2");
        set.add("3");

        set.remove("3");

        Iterator<String> it = set.iterator();
//        client.del("testset");
        while (it.hasNext()) {
            client.sadd("testset", it.next());
        }

        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        Student student = null;
        map.put("a", student);
        map.put("b", student);

        System.out.println(map.get("a"));

        System.out.println(client.smembers("testset"));

        client.close();
    }

    @Test
    public void test2() {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        Student student = null;
        map.put("a", student);
        map.put("b", student);

        System.out.println(map.get("a"));
    }

    @Test
    public void test3() {
        init("127.0.0.1:6379");
        ShardedJedis client = getInstance();
//        client.hincrBy("key", "acda", 1);
        client.set("a", "value");
        client.set("b", "value");
        client.set("c", "value");
    }

    @Test
    public void test4() {
        init("127.0.0.1:6379");
        ShardedJedis client = getInstance();
//        client.hincrBy("key", "acda", 1);
        String[] strs = new String[] {"a","b","c"};
        Collection<Jedis> jediss = client.getAllShards();
        for (Jedis jedis : jediss) {
            jedis.del(strs);
        }
    }

    @Test
    public void test5() {
        System.out.println(null + "feq");
    }
}