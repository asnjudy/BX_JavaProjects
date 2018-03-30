package wait.consumer;

import java.util.LinkedList;

public class MyQueue<T> {

    private static final int DEFAULT_SIZE = 8;
    int size;
    private LinkedList<T> list = new LinkedList<T>();

    public MyQueue() {
        this.size = DEFAULT_SIZE;
    }

    public MyQueue(int size) {
        this.size = (size <= 0 ? DEFAULT_SIZE : size);
    }

    public synchronized void put(T t) {
        while (list.size() >= size) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        list.addLast(t);
        System.out.println("生产了：" + t);
        this.notifyAll();
    }

    public synchronized T get() {
        while (list.size() <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notifyAll();
        T t = list.removeFirst();
        System.out.println("消费了：" + t);
        return t;
    }
}
