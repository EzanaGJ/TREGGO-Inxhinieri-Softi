package Service;

import Model.Item;

public interface ItemFilterStrategy {
    boolean filter(Item item);
}
