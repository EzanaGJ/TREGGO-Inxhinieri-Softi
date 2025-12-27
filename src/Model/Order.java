package Model;

import java.util.Date;

public class Order {

    private int orderId;
    private int userId;
    private int addressId;
    private String status;
    private Date createdAt;

    // Constructor me ID (nga DB)
    public Order(int orderId, int userId, int addressId, String status, Date createdAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.addressId = addressId;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Constructor pa ID (për insert)
    public Order(int userId, int addressId, String status) {
        this.userId = userId;
        this.addressId = addressId;
        this.status = status;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}