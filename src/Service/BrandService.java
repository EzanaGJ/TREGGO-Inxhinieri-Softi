package Service;

import Model.Brand;

import java.util.List;
import java.util.Optional;

public class BrandService {

    public int brandId;
    private String name;

    // Constructor me ID (nga DB)
    public BrandService(int brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }

    // Constructor pa ID (kur krijohet i ri)
    public BrandService(String name) {
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

    public void createBrand(Brand brand) {
    }

    public Optional<Brand> getBrandById(int brandId) {
        return Optional.empty();
    }

    public boolean updateBrand(Brand brand) {
        return false;
    }

    public List<Brand> getAllBrands() {
        return List.of();
    }

    public boolean deleteBrand(int brandId) {
        return false;
    }
}