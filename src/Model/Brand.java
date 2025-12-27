package Model;

public class Brand {

    private int brandId;
    private String name;

    // Constructor me ID (nga DB)
    public Brand(int brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }

    // Constructor pa ID (kur krijohet i ri)
    public Brand(String name) {
        this.name = name;
    }

    // Getters
    public int getBrandId() {
        return brandId;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}