/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repo;

import sql.AccountRepositoryImpl;
import sql.CategoryRepositoryImpl;
import sql.HistoryRepositoryImpl;
import sql.ProductRepositoryImpl;

/**
 *
 * @author Nina
 */
public class RepositoryFactory {
    
    public RepositoryFactory() {
    }
    
    public static AccountRepositoryImpl getAccountRepository(){
        return new AccountRepositoryImpl();
    }
    
    public static CategoryRepositoryImpl getCategoryRepository(){
        return new CategoryRepositoryImpl();
    }
    
    public static ProductRepositoryImpl getProductRepository(){
        return new ProductRepositoryImpl();
    }
    
    public static HistoryRepositoryImpl getLoginHistoryRepository(){
        return new HistoryRepositoryImpl();
    }
}
