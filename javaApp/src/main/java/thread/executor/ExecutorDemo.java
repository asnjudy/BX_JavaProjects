package thread.executor;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created by xuwentang on 2017/10/22.
 */
public class ExecutorDemo {


    public static void testThreadPoolExecutor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
                System.out.println("11111");
            }
        };

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(runnable);
    }

    public static void testFuture(Callable callable) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Integer> result = executor.submit(callable);
        executor.shutdown();
        System.out.println("正在执行任务");
        Thread.sleep(1000);
        System.out.println("任务的运行结果为：" + result.get());
    }




    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(2000);
                int result = 0;
                for (int i = 1; i <= 100; i++) {
                    for (int j = 0; j < i; j++) {
                        result += j;
                    }
                }
                return result;
            }
        };

        FutureTask<Integer> result = new FutureTask<Integer>(callable);
        ExecutorService excutor = Executors.newCachedThreadPool();
        excutor.submit(result);
        excutor.shutdown();
        System.out.println("主线程正在执行任务");
        System.out.println("task运行结果为:" + result.get());

    }
}
