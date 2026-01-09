package Model;

public class Brand {

    public int brandId;
    private String name;

    public Brand(int brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }

    public Brand(String name) {
        this.name = name;
    }

    public int getBrandId() {
        return brandId;
    }

    public String getName() {
        return name;
    }

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