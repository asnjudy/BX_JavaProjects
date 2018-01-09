package com.asn.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xuwentang on 2017/8/14.
 */
public class AccessLimitaitonByIpAndUsername {


    private static final Logger LOG = LoggerFactory.getLogger(AccessLimitaitonByIpAndUsername.class);

    /**
     * 记录访问时间信息
     *      key     为 'IP + Username'
     *      value   为该次访问的时间戳
     */
    private static Map<String, ArrayBlockingQueue<Long>>  accessTimeMap = new ConcurrentHashMap<String, ArrayBlockingQueue<Long>>();

    /**
     * 对于过于频繁访问的‘IP+User’（超过指定的阈值），禁止访问
     */
    private static Map<String, Long> disabledAccessMap = new ConcurrentHashMap<String, Long>();

    private static final int ACCESS_THRESHOLD = 20;
    private static final long ACCESS_RANGE_IN_MILSECONDS = 60 * 1000; // 1分钟

    private static final long DISABLED_ACCESS_RANGE_IN_MILSECONDS = 6 * 1000; // 5分钟


    // 白名单
    private static Set<String> whiteSet = Collections.synchronizedSet(new HashSet<String>());

    static {
        whiteSet.add("localhost");
        whiteSet.add("127.0.0.1");
    }


    public boolean accessControl(String username, String sourceIp) {

        if (whiteSet.contains(sourceIp)) {
            return true;
        } else {
            return exceedsThreshold(username, sourceIp);
        }
    }


    /**
     * 根据统计信息进行访问限制
     * @param username
     * @param sourceIp
     * @return
     */
    public boolean exceedsThreshold(String username, String sourceIp) {

        boolean pass = true; // 默认放行
        String accessIdentity = sourceIp + username; // 标识该访问请求
        long currentTime = System.currentTimeMillis();

        try {

            if (!accessTimeMap.containsKey(accessIdentity)) {
                // 若该用户是第一次从该IP进行访问，则默认放行
                ArrayBlockingQueue<Long> queue = new ArrayBlockingQueue<Long>(ACCESS_THRESHOLD);
                queue.offer(currentTime);
                accessTimeMap.put(accessIdentity, queue);
            } else {
                // 如果被封号
                if (disabledAccessMap.containsKey(accessIdentity)) {
                    // 取出禁止访问时间
                    long disabledAccessTime = disabledAccessMap.get(accessIdentity);
                    // 如果还在禁用期内，则禁止访问（不放行）
                    if (currentTime - disabledAccessTime < DISABLED_ACCESS_RANGE_IN_MILSECONDS) {
                        pass = false;
                        LOG.info("admin@" + sourceIp + " 访问被拒绝");
                    }
                } else {
                    ArrayBlockingQueue<Long> queue = accessTimeMap.get(accessIdentity);
                    if (queue.size() == ACCESS_THRESHOLD) {
                        // 队列已满，先出后进
                        long startTime = queue.remove();
                        queue.offer(currentTime);
                        long interval =  currentTime - startTime;
                        if (interval < ACCESS_RANGE_IN_MILSECONDS) {
                            pass = false; // 禁止访问（不放行）
                            disabledAccessMap.put(accessIdentity, currentTime); // 添加禁止访问
                            LOG.info("admin@" + sourceIp + " 访问被拒绝");
                        }
                    } else {
                        // 队列未满，直接入队
                        queue.offer(currentTime);
                    }
                }
            }
        } catch (Exception e) {
            LOG.info(username + "@" + sourceIp + ", 登录访问限制失败，登录请求被放行；错误详情：" + e.getMessage());
        }

        LOG.info(username + "@" + sourceIp + " exceedsThreshold 处理耗时：" + (System.currentTimeMillis() - currentTime) + "ms");
        return pass;
    }

}
