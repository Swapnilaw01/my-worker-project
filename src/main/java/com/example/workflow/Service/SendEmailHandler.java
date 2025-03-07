package com.example.workflow.Service;

import com.example.workflow.Interface.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Service;

@Service("sendEmailHandler")
public class SendEmailHandler implements ExternalTaskHandler {
    @Override
    public void handleTask(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String email = externalTask.getVariable("email");
        System.out.println("Sending email to: " + email);
        externalTaskService.complete(externalTask);
    }
}

