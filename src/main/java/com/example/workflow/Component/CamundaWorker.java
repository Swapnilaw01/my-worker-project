package com.example.workflow.Component;

import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CamundaWorker implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // Connect to Camunda engine
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .workerId("java-worker")
                .lockDuration(10000) // milliseconds
                .build();

        // Subscribe to the topic "sendEmail"
        client.subscribe("sendEmailOld")
                .handler((externalTask, externalTaskService) -> {
                    // Retrieve variables from the process
                    String email = externalTask.getVariable("email");
                    String subject = externalTask.getVariable("subject");
                    String content = externalTask.getVariable("content");

                    // Simulate email sending logic
                    System.out.println("Sending email to: " + email);
                    System.out.println("Subject: " + subject);
                    System.out.println("Content: " + content);

                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}
