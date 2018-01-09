package redis.subpub;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.subpub.util.Commons;
import redis.subpub.util.FileUtils;
import redis.subpub.util.SerializationUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xuwentang on 2018/1/8.
 */
public class RedisMessageSubscriber {

    final static Logger logger = LoggerFactory.getLogger(RedisMessageSubscriber.class);


    static AtomicInteger COUNT_ATO = new AtomicInteger(0);
    static int COUNT = 0;




    public static void startPictureSubscriber() {

        final String host = Commons.REDIS_HOST;
        final int port = Commons.REDIS_PORT;
        final byte[] channel = Commons.CHANNEL;

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        BinaryJedis bjedis = null;
                        try {
                            bjedis = new BinaryJedis(host, port, 0);
                            logger.info("Subscribing to the {} channel of the redis({}:{}).", new String(channel), host, port);
                            // 当前线程会在该subscribe调用处阻塞，等待消息发送者往通道上发消息
                            bjedis.subscribe(new BinaryJedisPubSub() {
                                @Override
                                public void onMessage(byte[] channel, byte[] message) {
                                    Object obj = SerializationUtils.unserialize(message);
                                    if (obj == null) {
                                        // 传入的消息可能有问题，没有反序列化出来
                                        return;
                                    }
                                    if (obj instanceof JSONObject) {
                                        JSONObject jsonObj = (JSONObject) obj;
                                        logger.info("received json keys: " + jsonObj.keySet());

                                        byte[] picBytes = jsonObj.getBytes("picBytes");
                                        String picPathName = jsonObj.getString("picPathName");
                                        String sendThreadName = jsonObj.getString("sendThreadName");

                                        if (picBytes == null || picPathName == null) {
                                            logger.error("没有获得图片路径、图片字节内容");
                                            return;
                                        }
                                        // e:/var/add.jpg -> e:/var/main_add_1.jpg
                                        picPathName = String.format("%s%s_%s_%d.%s",
                                                picPathName.substring(0, picPathName.lastIndexOf("/") + 1), // e:/var/
                                                sendThreadName,
                                                picPathName.substring(picPathName.lastIndexOf("/") + 1, picPathName.lastIndexOf(".")), // add
                                                ++COUNT,
                                                picPathName.substring(picPathName.lastIndexOf(".") + 1));

                                        logger.info("save pic to the local file: " + picPathName);

                                        FileUtils.saveBytesToFile(picBytes, picPathName);
                                    } else {
                                        logger.error("请不要向CCTV1通道乱发布内容");
                                    }
                                }
                            }, channel);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error(e.getMessage());
                        } finally {
                            if (bjedis != null) {
                                bjedis.disconnect();
                            }
                        }
                        logger.error("The {} subscriber to the redis({}:{}) failed and stoped for some reason.",
                                new String(channel), host, port);
                    }
                },
                "Thread-" + new String(channel)
        ).start();
    }

    public static void main(String[] args) throws InterruptedException {

        startPictureSubscriber();
    }
}
