package Test;

import Model.Address;
import Model.Order;
import Model.Enum.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @BeforeEach
    void setUp() {
        Order.getOrderDatabase().clear();
        Order.idCounter = 1;
    }

    @Test
    void testCreateOrder() {
        Order order = new Order(1, 100.0);
        order.createOrder();

        assertEquals(1, Order.getOrderDatabase().size());
        assertEquals(1, order.orderId);
        assertEquals(OrderStatus.PENDING, order.status);
    }

    @Test
    void testCalculateTotal() {
        Order order = new Order(2, 75.5);
        double total = order.calculateTotal();
        assertEquals(75.5, total);
    }

    @Test
    void testAddAddress() {
        Order order = new Order(3, 50.0);
        Address address = new Address("Main Street", "Tirana", "1001");

        order.addAddress(address);

        assertNotNull(order.shippingAddr);
        assertEquals("Main Street", order.shippingAddr.street);
        assertEquals("Tirana", order.shippingAddr.city);
    }

    @Test
    void testGetTrackingNoGeneratedOnce() {
        Order order = new Order(4, 120.0);

        String tracking1 = order.getTrackingNo();
        String tracking2 = order.getTrackingNo();

        assertNotNull(tracking1);
        assertEquals(tracking1, tracking2);
    }

    @Test
    void testUpdateStatus() {
        Order order = new Order(5, 200.0);
        order.updateStatus(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, order.status);
    }

    @Test
    void testAssignBuyer() {
        Order order = new Order(6, 90.0);
        order.assignBuyer(101);
        assertEquals(101, order.buyerId);
    }

    @Test
    void testAssignSeller() {
        Order order = new Order(7, 140.0);
        order.assignSeller(202);
        assertEquals(202, order.sellerId);
    }

    @Test
    void testMultipleOrdersInDatabase() {
        Order o1 = new Order(8, 30.0);
        Order o2 = new Order(9, 60.0);

        o1.createOrder();
        o2.createOrder();

        assertEquals(2, Order.getOrderDatabase().size());
    }
}


