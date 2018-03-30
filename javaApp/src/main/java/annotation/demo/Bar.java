package annotation.demo;


@Foo("fff")
public class Bar {

    public static final int count = 12;

    private String val;

    @MFoo(app = "oss", version = "1.1.0", value = "wto")
    public String info() {
        return "bar info!!";
    }
}
