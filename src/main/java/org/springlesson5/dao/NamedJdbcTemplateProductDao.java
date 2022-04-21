package org.springlesson5.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springlesson5.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

//@Component
@RequiredArgsConstructor
public class NamedJdbcTemplateProductDao implements ProductDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Iterable<Product> findAll() {
        String sql = "SELECT * FROM product";
        return namedParameterJdbcTemplate.query(sql, new ProductMapper());
    }

    @Override
    public String findNameById(Long id) {
        String sql = "SELECT title FROM product WHERE id = :productId";
        Map<String, Object> namedParametrs = new HashMap<>();
        namedParametrs.put("productId", id);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParametrs, String.class);
    }

    @Override
    public Product findById(Long id) {
        String sql = "SELECT p.id as product_id, title, cost, MANUFACTURE_DATE, MANUFACTURER_ID\n" +
                "FROM PRODUCT m\n" +
                "WHERE p.id = :productId;";
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("productId", id);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new ManufacturerWithProductExtractor());
    }

    @Override
    public void insert(Product product) {

    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void deleteById(Long id) {

    }

    private static class ProductMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return org.springlesson5.entity.Product.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .build();
        }
    }

    private static class ManufacturerWithProductExtractor implements ResultSetExtractor<Product> {

        @Override
        public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
            Product product = null;
            while (rs.next()){
                if (product == null){
                    product = Product.builder()
                            .id(rs.getLong("id"))
                            .title(rs.getString("title"))
                            .cost(rs.getBigDecimal("cost"))
                            .date(rs.getDate("manufacture_date").toLocalDate())
                            .build();
                }
            }
            return product;
        }
    }
}
