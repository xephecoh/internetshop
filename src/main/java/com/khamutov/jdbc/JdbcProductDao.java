package com.khamutov.jdbc;

import com.khamutov.jdbc.dao.ProductDao;
import com.khamutov.entities.Product;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.List;


public class JdbcProductDao implements ProductDao {
    private final QueryExecutor queryExecutor;

    public JdbcProductDao(PGSimpleDataSource dataSource) {
        this.queryExecutor = new QueryExecutor(dataSource);
    }

    public List<Product> findAllProducts() {
        return queryExecutor.getAllProducts();
    }

    public void delete(int id) {
        queryExecutor.deleteItem(id);
    }

    public boolean update(Product product) {
        return queryExecutor.update(product);
    }

    public void save(Product product) {
        queryExecutor.save(product);
    }
}
