package org.khamutov;

import com.khamutov.entities.Product;
import com.khamutov.jdbc.RowMapper;
import com.khamutov.servlets.RequestBodyParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    public void testParser() {
        RequestBodyParser parser = new RequestBodyParser();
        String input = "name=Sword&price=156&id=32";
        Product product = parser.parseRequest(input).getProduct();
        assertEquals("Sword", product.getName());
        assertEquals(156, product.getPrice());
        assertEquals(32, product.getId());
    }

    @Test
    public void testRowMapper() throws SQLException {
        Product product = null;
        RowMapper rowMapper = new RowMapper();
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(resultSetMock.getString("name")).thenReturn("Cellphone");
        Mockito.when(resultSetMock.getInt("id")).thenReturn(12);
        Mockito.when(resultSetMock.getInt("price")).thenReturn(99);
        Mockito.when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        while (resultSetMock.next()) {
            product = rowMapper.getProduct(resultSetMock);
        }
        Product expectProduct = new Product(12,"Cellphone",99);
        assert product != null;
        assertEquals(expectProduct.getId(),product.getId());
        assertEquals(expectProduct.getName(),product.getName());
        assertEquals(expectProduct.getPrice(),product.getPrice());
        assertEquals(expectProduct.getClass(),product.getClass());
    }

    @Test
    public void testService(){



    }

}
