package com.example.workflow.Controller;

import com.example.workflow.Service.CamundaService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/camunda")
public class CamundaController {

    @Autowired
    private CamundaService camundaService;
    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("/start")
    public String startProcess(){
        return camundaService.startProcess();
    }

    @PostMapping("/start/process")
    public String startProcess(@RequestBody Map<String, Object> variables) {
        VariableMap variableMap = Variables.createVariables();
        variableMap.putAll(variables);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("MyWorkerProcess",
                variableMap
        );

        return "Process started: " + processInstance.getId();
    }

    @GetMapping("/task/complete")
    public String completeTask(){
        return camundaService.completeTask();
    }

    @PostMapping("/task/complete/process/{processInstanceId}")
    public String completeTaskForGivenInstance(@PathVariable String processInstanceId){
        return camundaService.completeTaskForGivenInstance(processInstanceId);
    }
}
