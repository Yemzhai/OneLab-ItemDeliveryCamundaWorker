package item.delivery.worker.client;

import item.delivery.worker.model.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "Item-Delivery-Catalog")
public interface CustomerClient {
    @RequestMapping("customer/{id}")
    CustomerDTO getCustomerById(@PathVariable("id") long id);
}
