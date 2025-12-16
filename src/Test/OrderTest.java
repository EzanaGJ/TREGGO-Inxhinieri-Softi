package Test;

import Model.Address;
import Model.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testCreateOrder() {
        Order order = new Order(10, 120.50);
        order.createOrder();

        assertNotNull(order.createdAt);
        assertEquals("CREATED", order.status);
        assertNotNull(order.getTrackingNo());
    }

    @Test
    void testCalculateTotal() {
        Order order = new Order(5, 99.99);
        assertEquals(99.99, order.calculateTotal());
    }

    @Test
    void testAddAddress() {
        Address address = new Address(1, "Albania", "Tirane", "1001", "Rruga e Kavajes");
        Order order = new Order(3, 50.0);

        order.addAddress(address);

        assertNotNull(order.shippingAddr);
        assertEquals("Tirane", order.shippingAddr.city);
    }

    @Test
    void testUpdateStatus() {
        Order order = new Order(7, 200.0);
        order.updateStatus("SHIPPED");

        assertEquals("SHIPPED", order.status);
    }

    @Test
    void testAssignBuyerAndSeller() {
        Order order = new Order(2, 30.0);

        order.assignBuyer(100);
        order.assignSeller(200);

        assertEquals(100, order.buyerId);
        assertEquals(200, order.sellerId);
    }
}
