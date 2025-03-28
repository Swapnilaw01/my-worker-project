package com.example.workflow.Component;

import com.example.workflow.Interface.ExternalTaskHandler;
import jakarta.annotation.PostConstruct;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GenericExternalTaskWorker {
    private final ApplicationContext context;
    private final Map<String, String> topicHandlerMap = Map.of(
            "sendEmail", "sendEmailHandler",
            "generateReport", "generateReportHandler"
    );

    @Autowired
    public GenericExternalTaskWorker(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    public void subscribeToTopics() {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:9090/engine-rest")
                .workerId("multi-topic-worker")
                .lockDuration(10000)
                .build();

        topicHandlerMap.forEach((topic, handlerBeanName) -> {
            client.subscribe(topic)
                    .handler((externalTask, externalTaskService) -> handleTask(topic, externalTask, externalTaskService))
                    .open();
        });
    }

    private void handleTask(String topic, ExternalTask externalTask, ExternalTaskService externalTaskService) {
        try {
            ExternalTaskHandler handler = (ExternalTaskHandler) context.getBean(topicHandlerMap.get(topic));
            handler.handleTask(externalTask, externalTaskService);
        } catch (Exception e) {
            System.err.println("Error processing task for topic: " + topic);
            e.printStackTrace();
            externalTaskService.handleFailure(externalTask, "Error", e.getMessage(), 0, 5000);
        }
    }
}
