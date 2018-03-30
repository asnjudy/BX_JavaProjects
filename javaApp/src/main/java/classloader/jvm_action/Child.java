package classloader.jvm_action;

import classloader.jvm_action.Parent.Parent;

public class Child extends Parent {

    static {
        System.out.println("Child init");
    }
}
