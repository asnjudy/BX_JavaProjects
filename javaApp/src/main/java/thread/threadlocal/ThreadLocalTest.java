package thread.threadlocal;

public class ThreadLocalTest {

    public static void testThreadLocal() {
        Thread t = new Thread() {

            ThreadLocal<String> mStringThreadLocal = new ThreadLocal<String>() {
                @Override
                protected String initialValue() {
                    return Thread.currentThread().getName();
                }
            };

            @Override
            public void run() {
                super.run();
                mStringThreadLocal.set("droidyue.com");
                System.out.println(mStringThreadLocal.get());
            }
        };
        t.start();
    }


    public static void main(String[] args) {

        testThreadLocal();
    }
}
