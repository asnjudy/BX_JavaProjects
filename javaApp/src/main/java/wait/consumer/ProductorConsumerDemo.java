package wait.consumer;

public class ProductorConsumerDemo {

    public static void main(String[] args) {

        MyQueue<IPhone> queue = new MyQueue<IPhone>();
        new Thread(new Productor("生产者1" , queue)).start();
        new Thread(new Consumer("消费者1" , queue)).start();

    }
}
