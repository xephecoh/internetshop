package com.khamutov.jdbc;

import com.khamutov.dao.ConnectionFactory;
import com.khamutov.dao.MyH2DataSource;
import com.khamutov.dao.ProductDao;
import com.khamutov.entities.Product;
import com.khamutov.jdbc.QueryExecutor;

import javax.sql.DataSource;
import java.util.List;


public class JdbcProductDao implements ProductDao {
    private final QueryExecutor queryExecutor;

    public JdbcProductDao(MyH2DataSource dataSource) {
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
