package thread.threadlocal.dateformat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateUtil {

    /** 锁对象 */
    private static final Object lockObj = new Object();

    /** 存放不同的日期模板格式的sdf的Map */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();



    private static SimpleDateFormat getSimpleDateFormat(final String pattern) {
        ThreadLocal<SimpleDateFormat> threadLocalSdf = sdfMap.get(pattern);

        if (threadLocalSdf == null) {
            synchronized (lockObj) {
                threadLocalSdf = sdfMap.get(pattern);
                if (threadLocalSdf == null) {
                    // 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                    System.out.println("put new sdf of pattern " + pattern + " to map");

                    threadLocalSdf = new ThreadLocal<SimpleDateFormat>() {
                        /**
                         * 每个线程的初始值
                         * @return
                         */
                        @Override
                        protected SimpleDateFormat initialValue() {
                            System.out.println("thread: " + Thread.currentThread() + " init pattern: " + pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, threadLocalSdf);
                }
            }
        }

        return threadLocalSdf.get();
    }


    public static String format(Date date, String pattern) {
        return getSimpleDateFormat(pattern).format(date);
    }


    public static Date parse(String dateStr, String pattern) throws ParseException {
        return getSimpleDateFormat(pattern).parse(dateStr);
    }


    public static void main(String[] args) {

        final String patten1 = "yyyy-MM-dd";
        final String patten2 = "yyyy-MM";

        Thread t1 = new Thread() {

            @Override
            public void run() {
                try {
                    System.out.println(DateUtil.parse("1992-09-13", patten1));;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t2 = new Thread() {

            @Override
            public void run() {
                try {
                    System.out.println(DateUtil.parse("2000-09", patten2));;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        System.out.println("双线程执行: ");
        ExecutorService exec2 = Executors.newFixedThreadPool(2);
        exec2.execute(t1);
        exec2.execute(t2);
        exec2.shutdown();
    }
}
