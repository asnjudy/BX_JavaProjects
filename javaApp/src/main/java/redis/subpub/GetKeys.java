package redis.subpub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.subpub.util.Commons;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xuwentang on 2018/1/22.
 */
public class GetKeys {

    final static Logger logger = LoggerFactory.getLogger(GetKeys.class);
    final static AtomicInteger COUNT_ATO = new AtomicInteger(0);

    final static JedisPool jedisPool;

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(4);
        poolConfig.setMaxIdle(4);
        jedisPool = new JedisPool(poolConfig, Commons.REDIS_HOST, Commons.REDIS_PORT);
    }


    public static void testJedisWithPool() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Jedis jedis = jedisPool.getResource();
                    long start = System.currentTimeMillis();
                    int count = COUNT_ATO.getAndIncrement();
                    Set<String> keys = jedis.keys("*");
                    logger.info("[{}] keys: {}, {}ms.", count, keys, System.currentTimeMillis() - start);
                    jedis.close();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public static void testJedisWithoutPool() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Jedis jedis = new Jedis(Commons.REDIS_HOST, Commons.REDIS_PORT);
                    long start = System.currentTimeMillis();
                    int count = COUNT_ATO.getAndIncrement();
                    Set<String> keys = jedis.keys("*");
                    logger.info("[{}] keys: {}, {}ms.", count, keys, System.currentTimeMillis() - start);
                    jedis.close();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public static void main(String[] args) {

        testJedisWithPool();
    }
}
