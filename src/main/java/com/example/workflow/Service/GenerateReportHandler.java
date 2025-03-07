package com.example.workflow.Service;

import com.example.workflow.Interface.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("generateReportHandler")
public class GenerateReportHandler implements ExternalTaskHandler {
    @Override
    public void handleTask(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String reportType = externalTask.getVariable("reportType");
        System.out.println("Generating report: " + reportType);
        Map<String, Object> variables = new HashMap<>();
        variables.put("reportName", "ABC Report");
        variables.put("reportedBy", "Swapnil");
        externalTaskService.complete(externalTask, variables);
    }
}
