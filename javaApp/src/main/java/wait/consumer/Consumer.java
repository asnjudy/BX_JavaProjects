package wait.consumer;

public class Consumer implements Runnable {

    private String name;
    private MyQueue<IPhone> queue;

    public Consumer(String name, MyQueue queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            IPhone iPhone = queue.get();
            try {
                Thread.sleep((int) (Math.random() * 200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
