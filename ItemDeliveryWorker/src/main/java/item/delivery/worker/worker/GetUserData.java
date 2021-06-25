package item.delivery.worker.worker;

import item.delivery.worker.client.CustomerClient;
import item.delivery.worker.model.CustomerDTO;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ExternalTaskSubscription(topicName = "userData")
public class GetUserData implements ExternalTaskHandler {
    @Autowired
    private CustomerClient customer;
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        long customerId = externalTask.getVariable("customerId");
        customer.getCustomerById(customerId);
        System.out.println(customer.getCustomerById(customerId));
        VariableMap variables = Variables.createVariables();
        variables.put("customerId", customerId);
        externalTaskService.complete(externalTask, variables);
    }
}
