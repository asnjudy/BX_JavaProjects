package com.asn.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by xuwentang on 2017/8/14.
 */
public class Worker implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Worker.class);


    static final String[] IP_LIST = {"192.168.2.12", "192.168.2.13", "192.168.2.14", "192.168.2.15"};

    public static String getIp() {
        Random random = new Random(2);
        int index = random.nextInt(3);
        return IP_LIST[index];
    }


    CyclicBarrier barrier;

    public Worker(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {

        try {
            LOG.info("Worker's is waiting.....");
            barrier.await(); //
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        LOG.info("admin  -------start-------");
        for (int i = 0; i < 30; i++) {
            String ip = getIp();
            AccessLimitaitonByIpAndUsername al = new AccessLimitaitonByIpAndUsername();
            LOG.info("开始对 admin@" + ip + " 进行访问限制测试");
            if (al.accessControl("admin", ip)) {
                LOG.info("admin@" + ip + " 访问限制测试通过");
            }
        }
        LOG.info("admin  -------end-------");
    }
}