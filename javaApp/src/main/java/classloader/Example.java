package classloader;


public class Example implements IExample {
    private int counter;

    public String message() {
        return "Version 1";
    }

    public int plusPlus() {
        return counter++;
    }

    public int counter() {
        return counter;
    }

    public IExample copy(IExample example) {
        if (example != null) {
            counter = example.counter();
        }
        return this;
    }
}
