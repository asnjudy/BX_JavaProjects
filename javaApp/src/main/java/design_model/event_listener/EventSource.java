package design_model.event_listener;

import java.util.Vector;


/**
 * 事件源
 */
public class EventSource {

    private Vector<EventListener> listeners = new Vector<EventListener>();

    // 注册监听器
    public void addListener(EventListener eventListener) {
        listeners.add(eventListener);
    }

    // 撤销注册
    public void removeListener(EventListener eventListener) {
        listeners.remove(eventListener);
    }

    // 接收外部事件，通知监听器
    public void notifyListenerEvents(EventObject event) {
        for (EventListener listener : listeners) {
            listener.handleEvent(event);
        }
    }
}
