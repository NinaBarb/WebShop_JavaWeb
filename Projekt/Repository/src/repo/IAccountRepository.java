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
public interface IAccountRepository {
    int createAccount(UserAccount account) throws Exception;
    boolean doesAccountExist(String email) throws Exception;
    Optional<UserAccount> getAccount(String email, String password) throws Exception;
}
