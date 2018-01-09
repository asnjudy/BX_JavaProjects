package org.activiti.engine.impl.interceptor;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.SchemaOperationsProcessEngineBuild;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.event.logger.EventLoggerListener;
import org.activiti.spring.SpringTransactionContextFactory;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by xuwentang on 2017/11/3.
 */
public class CommandContextTest {


    public static void main(String[] args) {
        InputStream inputStream = CommandContextTest.class.getClassLoader().getResourceAsStream("chapter6/activiti.cfg.xml");
        ProcessEngineConfigurationImpl processEngineConfig = (ProcessEngineConfigurationImpl) ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(inputStream);
        processEngineConfig.buildProcessEngine();


        CommandContextFactory commandContextFactory = new CommandContextFactory();
        commandContextFactory.setProcessEngineConfiguration(processEngineConfig);



        CommandContext commandContext = Context.getCommandContext();
        commandContext.addCloseListener(new CommandContextCloseListener() {

            @Override
            public void closing(CommandContext commandContext) {
                System.out.println(" => closing ");
            }

            @Override
            public void closed(CommandContext commandContext) {
                System.out.println(" => closed");
            }

        });



    }



}
