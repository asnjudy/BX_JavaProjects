package java_test;

import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;

import java.util.Stack;

/**
 * Created by xuwentang on 2017/11/3.
 */
public class ThreadLocalTest {

    protected static ThreadLocal<Stack<CommandContext>> commandContextThreadLocal = new ThreadLocal<Stack<CommandContext>>();

    @Test
    public void test() {

        Stack<CommandContext> stack = commandContextThreadLocal.get();

        if (stack == null) {
            stack = new Stack<CommandContext>();
            commandContextThreadLocal.set(stack);
        }


        if (stack.isEmpty()) {
            return;
        }

        CommandContext commandContext = stack.peek();
        System.out.println(commandContext);
    }
}
