package item.delivery.worker.model;

import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class CustomerDTO {
    private long id;
    private String name;
    private int money;
    private Set<ItemDTO> itemsList;
}
