package classloader.jvm_action;

public class FinalFieldClass {

    public static final String constString = "const";

    static {
        System.out.println("FinalFieldClass init");
    }
}
