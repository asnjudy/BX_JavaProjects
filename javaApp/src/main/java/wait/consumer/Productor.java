package wait.consumer;

public class Productor implements Runnable {

    private String name;
    private MyQueue queue;

    public Productor(String name, MyQueue queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            IPhone iPhone  = new IPhone(i,"name-" + i);
            queue.put(iPhone);

            try {
                Thread.sleep((int) (Math.random() * 200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
