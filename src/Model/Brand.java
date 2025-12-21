package Model;

public class Brand {
    public int brandId;
    String name;

    public Brand(int brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}