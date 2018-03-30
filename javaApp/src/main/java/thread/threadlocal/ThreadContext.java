package thread.threadlocal;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {


    /**
     * 线程上下文变量的持有者
     */
    private final static ThreadLocal<Map<String, Object>> CTX_HOLDER = new ThreadLocal<Map<String, Object>>();

    static {
        CTX_HOLDER.set(new HashMap<String, Object>());
    }


    /**
     * 添加内容到线程上下文中
     * @param key
     * @param value
     */
    public final static void putContext(String key, Object value) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return;
        }
        ctx.put(key, value);
    }


    /**
     * 从线程上下文中获取内容
     * @param key
     * @param <T>
     * @return
     */
    public final static <T extends Object> T getContext(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return (T) ctx.get(key);
    }


    public final static Map<String, Object> getContext() {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return ctx;
    }




}
