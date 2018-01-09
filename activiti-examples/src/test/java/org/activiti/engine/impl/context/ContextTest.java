package org.activiti.engine.impl.context;

import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;

/**
 * Created by xuwentang on 2017/11/3.
 */
public class ContextTest {

    @Test
    public void test() {

        // Events are flushed when command context is closed
        CommandContext currentCommandContext = Context.getCommandContext();

        System.out.println(currentCommandContext);
    }
}
