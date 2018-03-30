package constant;


import java.io.Serializable;

public class Config implements Serializable {

    public static final String  common = new String("ddddd");


    static {

        System.out.println("### static block");
        System.out.println("### static block");
        System.out.println("### static block");
        System.out.println("### static block");
    }



}
