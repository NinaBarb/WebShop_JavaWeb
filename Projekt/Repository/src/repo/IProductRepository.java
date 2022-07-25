/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repo;

import java.util.List;
import java.util.Optional;
import models.Product;

/**
 *
 * @author Nina
 */
public interface IProductRepository {
    int createProduct(Product product)throws Exception;
    boolean doesProductExist(String title)throws Exception;
    List<Product> getProducts()throws Exception;
    List<Product> getProductsByCategory(int id)throws Exception;
    Optional<Product> getProduct(int id)throws Exception;
    void deleteProduct(int id)throws Exception;
    void updateProduct(Product product)throws Exception;
}
