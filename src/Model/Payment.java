package Model;

class Payment {
    final int paymentId;
    final int userId;
    final double amount;
    String method;
    PaymentStatus status;
    Date created = new Date();
    Payment(int id, int userId, double amount, String method)
    {   this.paymentId = id;
        this.userId = userId;
        this.amount = amount;
        this.method = method;
        this.status = PaymentStatus.PENDING; }
    @Override public String toString(){
        return String.format("Payment{id=%d,user=%d,amount=%.2f,method=%s,status=%s}", paymentId,userId,amount,method,status); }
}