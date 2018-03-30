package design_model.event_listener;

public class Test {

    public static void main(String[] args) {

        // 创建事件源，窗体本身作为事件源
        EventSource windows = new EventSource();

        // 注册窗体事件监听器
        windows.addListener(new WindowsEventListener());

        // 以事件对象为参数，触发事件监听器执行
        windows.notifyListenerEvents(new EventObject("closeWindows"));
    }

}
