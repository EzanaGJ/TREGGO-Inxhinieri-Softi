package Model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class OrderTest {

    @Test
    void testConstructorWithId() {
        Date now = new Date();
        Order order = new Order(1, 10, 20, "PENDING", now);

        assertEquals(1, order.getOrderId());
        assertEquals(10, order.getUserId());
        assertEquals(20, order.getAddressId());
        assertEquals("PENDING", order.getStatus());
        assertEquals(now, order.getCreatedAt());
    }

    @Test
    void testConstructorWithoutId() {
        Order order = new Order(10, 20, "CREATED");

        assertEquals(10, order.getUserId());
        assertEquals(20, order.getAddressId());
        assertEquals("CREATED", order.getStatus());
        assertEquals(0, order.getOrderId());
        assertNull(order.getCreatedAt());
    }

    @Test
    void testSettersAndGetters() {
        Order order = new Order(10, 20, "CREATED");
        Date now = new Date();

        order.setOrderId(5);
        order.setUserId(15);
        order.setAddressId(25);
        order.setStatus("SHIPPED");
        order.setCreatedAt(now);

        assertEquals(5, order.getOrderId());
        assertEquals(15, order.getUserId());
        assertEquals(25, order.getAddressId());
        assertEquals("SHIPPED", order.getStatus());
        assertEquals(now, order.getCreatedAt());
    }
}
