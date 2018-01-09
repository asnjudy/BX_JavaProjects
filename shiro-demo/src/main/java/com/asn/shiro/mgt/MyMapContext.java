package com.asn.shiro.mgt;

import org.apache.shiro.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Created by xuwentang on 2017/8/31.
 */
public class MyMapContext implements Map<String, Object>, Serializable {

    private static final long serialVersionUID = 5373399119017820322L;

    private final Map<String, Object> backingMap;

    public MyMapContext() {
        this.backingMap = new HashMap<String, Object>();
    }

    public MyMapContext(Map<String, Object> map) {
        this();
        if (!CollectionUtils.isEmpty(map)) {
            this.backingMap.putAll(map);
        }
    }


    /**
     * 执行get操作，但会额外确保返回的值是指定的类型
     * 如果没有值，返回null
     * @param key
     * @param type
     * @param <E>
     * @return
     */
    protected <E> E getTypedValue(String key, Class<E> type) {
        E found = null;
        Object o = backingMap.get(key);
        if (o != null) {
            if (!type.isAssignableFrom(o.getClass())) {
                String msg = "Invalid object found in SubjectContext Map under key [" + key + "]. Expected type "
                        + "was [" + type.getName() + "], but the object under that key is of type "
                        + "[" + o.getClass().getName() + "]";
                throw new IllegalArgumentException(msg);
            }
            found = (E) o;
        }
        return found;
    }


    /**
     * 在map上下文中，在指定的key下，放入一个value，仅当给定的value不为null
     * @param key
     *      在属性key下，存储非null值
     * @param value
     *
     */
    protected void nullSafePut(String key, Object value) {
        if (value != null) {
            put(key, value);
        }
    }

    @Override
    public int size() {
        return backingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return backingMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return backingMap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return backingMap.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return backingMap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return backingMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        backingMap.putAll(m);
    }

    @Override
    public void clear() {
        backingMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return Collections.unmodifiableSet(backingMap.keySet());
    }

    @Override
    public Collection<Object> values() {
        return Collections.unmodifiableCollection(backingMap.values());
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return Collections.unmodifiableSet(backingMap.entrySet());
    }
}
