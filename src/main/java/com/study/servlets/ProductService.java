package com.study.lab1.servlets;

import java.util.List;

public class ProductService {
    private ProductDao productDao;
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }
    public List<Product>  getProductList(){
        return productDao.getAllProducts();
    }
    public void  deleteById(int id){
        productDao.deleteById(id);
    }
}
