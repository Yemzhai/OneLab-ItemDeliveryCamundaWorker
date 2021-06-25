package item.delivery.worker.worker;

import item.delivery.worker.client.CustomerClient;
import item.delivery.worker.client.ItemClient;
import item.delivery.worker.model.CustomerDTO;
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
@ExternalTaskSubscription(topicName = "payment")
public class Payment implements ExternalTaskHandler {
    @Autowired
    private CustomerClient customer;
    @Autowired
    private ItemClient item;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        long customerId = externalTask.getVariable("customerId");
        long itemId = externalTask.getVariable("itemId");
        CustomerDTO customerDTO = customer.getCustomerById(customerId);
        ItemDTO choseItem = item.getItemById(itemId);
        boolean enoughMoney;
        enoughMoney = customerDTO.getMoney() + choseItem.getPrice() >= 0;
        if(enoughMoney){
            customerDTO.setMoney(customerDTO.getMoney()-choseItem.getPrice());
            choseItem.setQuantity(choseItem.getQuantity()-1);
//            customerDTO.setItemsList(customerDTO.getItemsList().add(choseItem));
        }
        System.out.println(customerDTO);
        System.out.println(choseItem);
        VariableMap variables = Variables.createVariables();
        variables.putValue("customerDTO",customerDTO);
        variables.putValue("choseItem",choseItem);

        externalTaskService.complete(externalTask, variables);
    }
}
