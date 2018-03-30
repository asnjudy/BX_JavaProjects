package internal_interface.util;
import internal_interface.util.Util.Worker;

public class UtilTest {

    public static void main(String[] args) {

        Worker worker = new Worker() {
            @Override
            public void work() {
                System.out.println("Is working");
            }
        };
        worker.work();


    }
}
