package design_model.event_listener;

public class WindowsEventListener implements EventListener {


    @Override
    public void handleEvent(EventObject event) {

        event.doEvent();

        if (event.getSource().equals("closeWindows")) {
            System.out.println("doClose");
        } else if (event.getSource().equals("openWindows")) {
            System.out.println("doOpen");
        }
    }
}
