/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import models.Category;
import repo.ICategoryRepository;


public class CategoryRepositoryImpl implements ICategoryRepository {
    
    private static final String ID_CATEGORY = "IDCategory";
    private static final String TITLE = "Title";
    private static final String PICTURE_PATH = "PicturePath";

    private static final String CREATE_CATEGORY = "{ CALL createCategory (?,?,?) }";
    private static final String SELECT_CATEGORIES = "{ CALL getCategories () }";
    private static final String EXISTS = "{ CALL doesCategoryExist (?) }";
    private static final String DELETE_CATEGORY = "{ CALL deleteCategory (?) }";
    private static final String GET_CATEGORY = "{ CALL getCategory (?) }";
    private static final String UPDATE_CATEGORY = "{ CALL updateCategory (?,?,?) }";

    @Override
    public int createCategory(Category category) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_CATEGORY)) {

            stmt.setString(1, category.getTitle());
            stmt.setString(2,category.getPicturePath());
            stmt.registerOutParameter(3, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }

    @Override
    public List<Category> getCategories() throws Exception {
        List<Category> categories = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_CATEGORIES);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt(ID_CATEGORY),
                        rs.getString(TITLE),
                        rs.getString(PICTURE_PATH));
                categories.add(category);
            }
        }
        return categories;
    }

    @Override
    public Optional<Category> getCategory(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_CATEGORY)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Category(
                            rs.getInt(ID_CATEGORY), 
                            rs.getString(TITLE), 
                            rs.getString(PICTURE_PATH)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean doesCategoryExist(String title) throws Exception {
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
    public void deleteCategory(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_CATEGORY)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void updateCategory(Category category) throws Exception {
        File f = new File(category.getPicturePath());
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                FileInputStream fs = new FileInputStream(f);
                CallableStatement stmt = con.prepareCall(UPDATE_CATEGORY)) {

            stmt.setInt(1, category.getId());
            stmt.setString(2, category.getTitle());
            stmt.setBinaryStream(3,fs,(int)f.length());
            
            stmt.executeUpdate();
        }
    }
    
}
