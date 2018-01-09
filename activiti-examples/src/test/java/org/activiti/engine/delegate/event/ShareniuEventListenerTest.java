package org.activiti.engine.delegate.event;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Before;
import org.junit.Test;

import chapter6.ShareniuEventListener;

public class ShareniuEventListenerTest {


    ProcessEngine processEngine;
    RepositoryService repositoryService;
    IdentityService identityService;
    RuntimeService runtimeService;
    TaskService taskService;

    String resourceName = "shareniu_addInputStream.bpmn";

    ActivitiEventDispatcher eventDispatcher;


    @Before
    public void init() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("chapter6/activiti.cfg.xml");
        ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(inputStream);
        processEngine = config.buildProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        identityService = processEngine.getIdentityService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();

        eventDispatcher = ((ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration()).getEventDispatcher();
    }


    @Test
    public void startProcessInstanceById() {
        ShareniuEventListener listener = new ShareniuEventListener();
        eventDispatcher.addEventListener(listener);

    }

    @Test
    public void complete() {
        Map<String, Object> variable = new HashMap<String, Object>();
        variable.put("s", "s");
        variable.put("s1", "s1");
        String taskId = "9";
        taskService.complete(taskId, variable);
    }


}
