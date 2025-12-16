package com.pluralsight.data.mysql;

import com.pluralsight.data.ProductDao;
import org.springframework.stereotype.Component;
import com.pluralsight.model.Product;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



@Component
public class MySqlProductDao extends MySqlDaoBase implements ProductDao {

    public MySqlProductDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String subCategory) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products " +
                "WHERE (category_id = ? OR ? = -1) " +
                "  AND (price >= ? OR ? = -1) " +
                "  AND (price <= ? OR ? = -1) " +
                "  AND (subcategory = ? OR ? = '') ";

        categoryId = categoryId == null ? -1 : categoryId;
        minPrice = minPrice == null ? new BigDecimal("-1") : minPrice;
        maxPrice = maxPrice == null ? new BigDecimal("-1") : maxPrice;
        subCategory = subCategory == null ? "" : subCategory;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ps.setInt(2, categoryId);

            ps.setBigDecimal(3, minPrice);
            ps.setBigDecimal(4, minPrice);

            ps.setBigDecimal(5, maxPrice);
            ps.setBigDecimal(6, maxPrice);

            ps.setString(7, subCategory);
            ps.setString(8, subCategory);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public List<Product> listByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE category_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public Product getById(int productId) {
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product create(Product product) {
        String sql = "INSERT INTO products " +
                "(name, price, category_id, description, subcategory, stock, image_url, featured) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setInt(3, product.getCategoryId());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getSubCategory());
            ps.setInt(6, product.getStock());
            ps.setString(7, product.getImageUrl());
            ps.setBoolean(8, product.isFeatured());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    product.setProductId(keys.getInt(1));
                }
            }

            return product;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int productId, Product product) {
        String sql = "UPDATE products SET " +
                "name=?, price=?, category_id=?, description=?, subcategory=?, stock=?, image_url=?, featured=? " +
                "WHERE product_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setInt(3, product.getCategoryId());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getSubCategory());
            ps.setInt(6, product.getStock());
            ps.setString(7, product.getImageUrl());
            ps.setBoolean(8, product.isFeatured());
            ps.setInt(9, productId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setCategoryId(rs.getInt("category_id"));
        p.setDescription(rs.getString("description"));
        p.setSubCategory(rs.getString("subcategory"));
        p.setStock(rs.getInt("stock"));
        p.setImageUrl(rs.getString("image_url"));
        p.setFeatured(rs.getBoolean("featured"));
        return p;
    }
}
