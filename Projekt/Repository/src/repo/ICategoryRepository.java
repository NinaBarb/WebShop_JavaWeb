/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repo;

import java.util.List;
import java.util.Optional;
import models.Category;

/**
 *
 * @author Nina
 */
public interface ICategoryRepository {
    
    int createCategory(Category category)throws Exception;
    boolean doesCategoryExist(String title)throws Exception;
    List<Category> getCategories()throws Exception;
    Optional<Category> getCategory(int id)throws Exception;
    void deleteCategory(int id)throws Exception;
    void updateCategory(Category category)throws Exception;
}
