package Model;

class Brand {
    int brandId;
    String name;

    public Brand(int brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }

    void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    String getName() {
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}