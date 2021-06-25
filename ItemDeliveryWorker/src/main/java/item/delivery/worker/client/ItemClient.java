package item.delivery.worker.client;

import item.delivery.worker.model.ItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "Item-Delivery-Catalog")
public interface ItemClient {
    @RequestMapping("item/{id}")
    ItemDTO getItemById(@PathVariable("id") long id);
}
