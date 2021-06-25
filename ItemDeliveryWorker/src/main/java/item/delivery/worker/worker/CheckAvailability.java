package item.delivery.worker.worker;

import item.delivery.worker.client.ItemClient;
import item.delivery.worker.model.ItemDTO;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ExternalTaskSubscription(topicName = "checkAvailability")
public class CheckAvailability implements ExternalTaskHandler {
    @Autowired
    private ItemClient item;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        long itemId = externalTask.getVariable("itemId");
        ItemDTO choseItem = item.getItemById(itemId);
        boolean available = choseItem.getQuantity() > 0;
        VariableMap variables = Variables.createVariables();
        variables.put("available", available);
        externalTaskService.complete(externalTask, variables);
    }
}
