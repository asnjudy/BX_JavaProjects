package com.asn.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by xuwentang on 2017/8/14.
 */
public class AccessTest {

    private static final Logger LOG = LoggerFactory.getLogger(AccessTest.class);
    private static final int THREAD_NUM = 20;


    public static void main(String[] args) throws InterruptedException {

        final CyclicBarrier cb = new CyclicBarrier(THREAD_NUM);

        for (int i = 0; i < THREAD_NUM; i++) {
            Thread thread = new Thread(new Worker(cb));
            thread.start();
        }

    }
}
