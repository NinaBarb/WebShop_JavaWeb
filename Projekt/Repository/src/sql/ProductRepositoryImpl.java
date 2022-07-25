/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import models.Product;
import repo.IProductRepository;

public class ProductRepositoryImpl implements IProductRepository {

    private static final String ID_PRODUCT = "IDProduct";
    private static final String TITLE = "Title";
    private static final String DESCRIPTION = "Descr";
    private static final String PRICE = "Price";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String CATEGORY_ID = "CategoryID";

    private static final String CREATE_PRODUCT = "{ CALL createProduct (?,?,?,?,?,?) }";
    private static final String SELECT_PRODUCTS = "{ CALL getProducts () }";
    private static final String EXISTS = "{ CALL doesProductExist (?) }";
    private static final String DELETE_PRODUCT = "{ CALL deleteProduct (?) }";
    private static final String GET_PRODUCT = "{ CALL getProduct (?) }";
    private static final String GET_PRODUCTS_BY_CATEGORY = "{ CALL getProductsByCategory (?) }";
    private static final String UPDATE_PRODUCT = "{ CALL updateProduct (?,?,?,?,?,?) }";

    @Override
    public int createProduct(Product product) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_PRODUCT)) {

            stmt.setString(1, product.getTitle());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getPicturePath());
            stmt.setInt(5, product.getCategoryId());
            stmt.registerOutParameter(6, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(6);
        }
    }

    @Override
    public boolean doesProductExist(String title) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(EXISTS)) {

            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Product> getProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PRODUCTS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt(ID_PRODUCT),
                        rs.getString(TITLE),
                        rs.getString(DESCRIPTION),
                        rs.getString(PICTURE_PATH),
                        rs.getDouble(PRICE),
                        rs.getInt(CATEGORY_ID));
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategory(int id) throws Exception {
        List<Product> products = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_PRODUCTS_BY_CATEGORY)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getInt(ID_PRODUCT),
                            rs.getString(TITLE),
                            rs.getString(DESCRIPTION),
                            rs.getString(PICTURE_PATH),
                            rs.getDouble(PRICE),
                            rs.getInt(CATEGORY_ID)));
                }
            }

            return products;
        }
    }

    @Override
    public Optional<Product> getProduct(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_PRODUCT)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Product(
                            rs.getInt(ID_PRODUCT),
                            rs.getString(TITLE),
                            rs.getString(DESCRIPTION),
                            rs.getString(PICTURE_PATH),
                            rs.getDouble(PRICE),
                            rs.getInt(CATEGORY_ID)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteProduct(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_PRODUCT)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void updateProduct(Product product) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_PRODUCT)) {

            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getTitle());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setString(5, product.getPicturePath());
            stmt.setInt(6, product.getCategoryId());

            stmt.executeUpdate();
        }
    }
}
