package design_model.event_listener;


/**
 * 事件对象
 */
public class EventObject extends java.util.EventObject {


    public EventObject(Object source) {
        super(source);
    }

    public void doEvent() {
        System.out.println("通知一个事件源 source: " + this.getSource());
    }
}
