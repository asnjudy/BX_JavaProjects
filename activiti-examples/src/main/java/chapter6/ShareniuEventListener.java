package chapter6;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiVariableEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEventImpl;

public class ShareniuEventListener implements ActivitiEventListener {



    @Override
    public void onEvent(ActivitiEvent event) {
        System.out.println(event + ", " + event.getType());
        switch (event.getType()) {
            case VARIABLE_CREATED:  // 变量创建事件
                ActivitiVariableEvent variableEvent = (ActivitiVariableEvent) event;

                break;

            case ENGINE_CREATED:  // 引擎创建事件
                ActivitiEventImpl activitiEventImpl = (ActivitiEventImpl) event;
                break;


            default:
                break;
        }
    }



    @Override
    public boolean isFailOnException() {
        return false;
    }

}
