package com.example.workflow.Service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamundaService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public String startProcess(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("MyWorkerProcess");
        return "Process started: "+processInstance.getId();
    }

    public String completeTask(){
        List<Task> tasks = taskService.createTaskQuery().orderByTaskId().desc().list();
        System.out.println("Task to complete: "+tasks.get(0));
        if(!tasks.isEmpty()){
            System.out.println("Task ID to complete: " + tasks.get(0));
            Task task = tasks.get(0);
            taskService.complete(task.getId());
            return "Completed task: "+task.getName();
        }else{
            return "No active task found";
        }
    }

    public String completeTaskForGivenInstance(String processInstanceId){
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        System.out.println("Complete task for process instance: "+processInstanceId);
        System.out.println("Task to complete: "+tasks.get(0));
        if(tasks.isEmpty()){
            return "No active tasks found for process instance: " + processInstanceId;
        }

        Task task = tasks.get(0);
        taskService.complete(task.getId());

        return "Complete task: "+task.getName() +"For Process instance Id: "+processInstanceId;
    }
}
