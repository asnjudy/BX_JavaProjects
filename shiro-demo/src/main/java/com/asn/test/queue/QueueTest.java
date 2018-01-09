package com.asn.test.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by xuwentang on 2017/8/16.
 */
public class QueueTest {

    private static final Logger LOG = LoggerFactory.getLogger(QueueTest.class);

    public static void main(String[] args) {


        /**
         * 上限为5的队列
         */
        ArrayBlockingQueue<Long> queue = new ArrayBlockingQueue<Long>(5);
        LOG.info("向有界队列添加12L " + queue.offer(12L));
        LOG.info("向有界队列添加13L " + queue.offer(13L));
        LOG.info("向有界队列添加14L " + queue.offer(14L));
        LOG.info("向有界队列添加15L " + queue.offer(15L));
        LOG.info("向有界队列添加16L " + queue.offer(16L));
        LOG.info("向有界队列添加17L " + queue.offer(17L));
        LOG.info("向有界队列添加18L " + queue.add(18L));
        LOG.info("向有界队列添加19L " + queue.offer(19L));

        System.out.println(1+3);
    }
}
