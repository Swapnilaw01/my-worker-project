package com.example.workflow.Interface;


import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;

public interface ExternalTaskHandler {
    void handleTask(ExternalTask externalTask, ExternalTaskService externalTaskService);
}
