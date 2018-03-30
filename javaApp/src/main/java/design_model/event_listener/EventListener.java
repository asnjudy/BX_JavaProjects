package design_model.event_listener;


/**
 * 事件监听器接口
 */
public interface EventListener extends java.util.EventListener {

    void handleEvent(EventObject event);
}
