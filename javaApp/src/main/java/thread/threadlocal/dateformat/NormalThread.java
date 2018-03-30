package thread.threadlocal.dateformat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NormalThread implements Runnable {

    SimpleDateFormat simpleDateLocal = new SimpleDateFormat("yyyyMMdd-HH:mm");

    @Override
    public void run() {
        System.out.println("Before Thread_" + Thread.currentThread().getName()
                + " : " + simpleDateLocal.format(new Date()));

        simpleDateLocal = new SimpleDateFormat();
        System.out.println("After Thread_" + Thread.currentThread().getName()
                + " : " + simpleDateLocal.format(new Date()));
    }
}
