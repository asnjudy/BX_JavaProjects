package classloader;

public class Main {



    public static void main(String[] args) throws InterruptedException {


        IExample example1 = ExampleFactory.newInstance();

        while (true) {
            IExample example2 = null;
            example2 = ExampleFactory.newInstance().copy(example2);

            System.out.println("1) " + example1.message() + " = " + example1.plusPlus());
            System.out.println("2) " + example2.message() + " = " + example2.plusPlus());
            System.out.println();

            Thread.currentThread().sleep(3000);
        }
    }
}
