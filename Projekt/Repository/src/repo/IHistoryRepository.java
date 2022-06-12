/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repo;

import java.util.List;
import models.LoginHistory;

/**
 *
 * @author Nina
 */
public interface IHistoryRepository {
    
    int createLoginHistory(LoginHistory loginHistory)throws Exception;
    List<LoginHistory> getLoginHistory() throws Exception;
}
