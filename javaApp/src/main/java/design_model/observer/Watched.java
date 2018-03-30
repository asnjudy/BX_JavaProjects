package design_model.observer;

import java.util.Observable;


/**
 * 被观察者
 */
public class Watched extends Observable {

    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }
}
