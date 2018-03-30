package thread.threadlocal.dateformat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatClass implements Runnable {

    ThreadLocal<SimpleDateFormat> simpleDateLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd-HH:mm");
        }
    };

    @Override
    public void run() {
        System.out.println("Before Thread_" + Thread.currentThread().getName() +
                " : " + simpleDateLocal.get().format(new Date()));

        simpleDateLocal.set(new SimpleDateFormat());
        System.out.println("After Thread_" + Thread.currentThread().getName() +
                " : " + simpleDateLocal.get().format(new Date()));
    }
}
