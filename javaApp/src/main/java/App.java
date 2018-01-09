import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by xuwentang on 2017/9/19.
 */
public class App {

    public static void main(String[] args) {

        ArrayBlockingQueue<Long> abq = new ArrayBlockingQueue<Long>(5);
        abq.offer(11l);
        abq.offer(22l);
        abq.offer(33l);
        abq.offer(44l);
        abq.offer(55l);
        abq.offer(66l);

        System.out.println(abq);

        System.out.println("hello world");
    }
}
