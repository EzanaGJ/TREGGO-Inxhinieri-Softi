class Item {
    final int itemId;
    final int sellerId;
    String title, description, brand;
    double price;
    Category category;
    Size size;
    boolean sold = false;
    Date createdAt = new Date();

    Item(int id, int sellerId, String title, String desc, double price, Category cat, Size size, String brand){
        this.itemId = id; this.sellerId = sellerId; this.title = title; this.description = desc; this.price = price; this.category = cat; this.size = size; this.brand = brand;
    }
    @Override public String toString(){
        return String.format("Item{id=%d,title='%s',price=%.2f,seller=%d,sold=%s}", itemId,title,price,sellerId,sold);
    }
}