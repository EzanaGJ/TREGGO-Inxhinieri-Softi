package Service;

import Model.Item;
import Model.Enum.ItemStatus;

public class StatusFilterStrategy implements ItemFilterStrategy {

    private final ItemStatus status;

    public StatusFilterStrategy(ItemStatus status) {
        this.status = status;
    }

    @Override
    public boolean filter(Item item) {
        return item.getStatus() != null && item.getStatus().equals(status.name());
    }
}
