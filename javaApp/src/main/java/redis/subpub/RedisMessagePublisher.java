package redis.subpub;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryJedis;
import redis.subpub.util.Commons;
import redis.subpub.util.FileUtils;
import redis.subpub.util.SerializationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by xuwentang on 2018/1/8.
 */
public class RedisMessagePublisher {

    final static Logger logger = LoggerFactory.getLogger(RedisMessagePublisher.class);


    public static void publish()  {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("picPathName", "e:/var/add.jpg");
        byte[] picBytes = null;
        try {
            //
            picBytes = FileUtils.getContent("C:\\Users\\xuwentang\\Downloads\\zzpic9060\\zzpic9060.JPG");
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("picBytes", picBytes);
        map.put("sendThreadName", Thread.currentThread().getName());

        JSONObject jsonObject = new JSONObject(map);
        byte[] bytes = SerializationUtils.serializeToBytes(jsonObject);


        // 默认给个10分钟超时，如果10分钟还没传上去一个图片就应该是有问题了
        BinaryJedis bjedis = new BinaryJedis(Commons.REDIS_HOST, Commons.REDIS_PORT, 600000);
        logger.info("publish message to the redis");
        bjedis.publish(Commons.CHANNEL, bytes);
    }


    /**
     * 并发发布测试
     * 同时，大量线程发布同样的内容
     */
    public static void testConcurrentPublish() {

        int concurrentThreadCout = 10;

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(concurrentThreadCout);

        for (int i = 0; i < concurrentThreadCout; ++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.info("running ...");
                    try {
                        Thread.sleep(2000); // 给个延时，方便查看
                        cyclicBarrier.await();

                        // 每个线程跑2次
                        for (int i = 0; i < 5; ++i) {
                            publish();
                            Thread.sleep(10);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void main(String[] args) throws IOException {

        testConcurrentPublish();
       // publish();
    }
}
