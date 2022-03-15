package com.khamutov.services;

import com.khamutov.jdbc.dao.ProductDao;
import com.khamutov.entities.Product;

import java.util.List;

public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getProductList() {
        return (List<Product>) productDao.findAllProducts();
    }

    public void deleteById(int id) {
        productDao.delete(id);
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public boolean update(Product product) {
        return productDao.update(product);
    }
}
