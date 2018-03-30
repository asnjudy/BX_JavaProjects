package classloader.jvm_action.dep_jar;

import com.alibaba.fastjson.JSONObject;

public class Test {

    static JSONObject jsonObject = new JSONObject();

    public void testJson() {
        System.out.println(new JSONObject());
    }

    public static void testJsonStatic() {
        System.out.println(new JSONObject());
    }


    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Hello");
        } else {
            System.out.println("Test the alibaba fastjson jar dependency");
            testJsonStatic();
            new Test().testJson();
        }
    }
}
