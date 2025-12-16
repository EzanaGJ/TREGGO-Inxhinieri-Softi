package Model;

import Model.Enum.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    Order order;

    @BeforeEach
    void setUp() {
        Order.orderDatabase.clear();
        Order.idCounter = 1;

        order = new Order(10, 150.0);
    }

    @Test
    void testCreateOrder() {
        order.createOrder();

        assertEquals(1, Order.orderDatabase.size());
        assertEquals(order, Order.orderDatabase.get(0));
    }

    @Test
    void testCalculateTotal() {
        double total = order.calculateTotal();

        assertEquals(150.0, total);
    }

    @Test
    void testAddAddress() {
        Address address = new Address(
                1,
                "Rruga A",
                "Tirana",
                "1001",
                "Albania"
        );

        order.addAddress(address);

        assertNotNull(order.shippingAddr);
        assertEquals("Rruga A", order.shippingAddr.street);
        assertEquals("Tirana", order.shippingAddr.city);
    }

    @Test
    void testGetTrackingNo() {
        String tracking1 = order.getTrackingNo();
        String tracking2 = order.getTrackingNo();

        assertNotNull(tracking1);
        assertEquals(tracking1, tracking2);
        assertTrue(tracking1.startsWith("TRK"));
    }

    @Test
    void testUpdateStatus() {
        order.updateStatus(OrderStatus.SHIPPED);

        assertEquals(OrderStatus.SHIPPED, order.status);
    }

    @Test
    void testAssignBuyer() {
        order.assignBuyer(5);

        assertEquals(5, order.buyerId);
    }

    @Test
    void testAssignSeller() {
        order.assignSeller(7);

        assertEquals(7, order.sellerId);
    }

    @Test
    void testListAllOrders() {
        order.createOrder();
        Order order2 = new Order(20, 300.0);
        order2.createOrder();

        assertEquals(2, Order.orderDatabase.size());
    }
}
