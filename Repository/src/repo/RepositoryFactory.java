/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repo;

import sql.SqlAccountRepository;
import sql.SqlItemRepository;

/**
 *
 * @author Nina
 */
public class RepositoryFactory {

    public RepositoryFactory() {
    }
    
    public static SqlAccountRepository getSqlAccountRepository(){
        return new SqlAccountRepository();
    }
    
    public static SqlItemRepository getSqlItemRepository(){
        return new SqlItemRepository();
    }
}
