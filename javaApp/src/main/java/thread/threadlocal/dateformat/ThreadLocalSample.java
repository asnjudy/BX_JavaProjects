package thread.threadlocal.dateformat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.threadlocal.dateformat.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ThreadLocalSample {

    final static Logger logger = LoggerFactory.getLogger(ThreadLocalSample.class);


    public static void testDateFormat() {

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HH:mm SSS");

        int concurrentThreadCout = 35;
        final int timesPerThread = 2;

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(concurrentThreadCout);

        for (int i = 0; i < concurrentThreadCout; ++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.info("running ...");
                    try {
                        cyclicBarrier.await();

                        // 每个线程跑2次
                        for (int i = 0; i < timesPerThread; ++i) {
                            // SimpleDateFormat 的 format 方法，不存在线程安全问题
                            System.out.println(Thread.currentThread().getName() + " : " + simpleDateFormat.format(new Date()));
                            // SimpleDateFormat 的 parse 方法，存在线程安全问题
                            System.out.println(Thread.currentThread().getName() + " : " + simpleDateFormat.parse("20180223-09:55 783"));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        System.out.println("emddddddddd");
    }


    public void testDateFormatThreadLocal() {
        DateFormatClass dateFormatClass = new DateFormatClass();

        for (int i = 1; i <= 100000; i++) {

            Thread t = new Thread(dateFormatClass, String.valueOf(i));
            t.start();
        }
    }


    /**
     * 使用 DateUtil 解析 日期字符串
     */
    public static void testDateUtil() {

        int concurrentThreadCout = 35;
        final int timesPerThread = 2;

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(concurrentThreadCout);

        for (int i = 0; i < concurrentThreadCout; ++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.info("ready ...");
                    try {
                        cyclicBarrier.await();

                        for (int i = 0; i < timesPerThread; ++i) {
                            // SimpleDateFormat 的 parse 方法，存在线程安全问题
                            Date date = DateUtil.parse("20180223-09:55 783", "yyyyMMdd-HH:mm SSS");
                            System.out.println(Thread.currentThread().getName() + " : " + date);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        System.out.println("emddddddddd");
    }


    public static void main(String[] args) {

        testDateUtil();
    }
}
