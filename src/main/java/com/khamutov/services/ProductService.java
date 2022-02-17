package com.khamutov.services;

import com.khamutov.entities.Product;

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
    public void save(int id,String name,int price){
        productDao.save(id,name,price);
    }
    public void update(int id,String name,int price){
        productDao.update(id,name,price);
    }
}
