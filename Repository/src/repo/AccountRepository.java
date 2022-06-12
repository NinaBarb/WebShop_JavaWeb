/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repo;

import java.util.Optional;
import models.UserAccount;

/**
 *
 * @author Nina
 */
public interface AccountRepository {
    int createAccount(UserAccount account) throws Exception;
    boolean doesAccountExist(String userName) throws Exception;
    Optional<UserAccount> selectAccount(String userName) throws Exception;
}
