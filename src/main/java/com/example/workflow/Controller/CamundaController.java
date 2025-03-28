package com.example.workflow.Controller;

import com.example.workflow.Service.CamundaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/camunda")
public class CamundaController {

    @Autowired
    private CamundaService camundaService;

    @GetMapping("/start")
    public String startProcess(){
        return camundaService.startProcess();
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
