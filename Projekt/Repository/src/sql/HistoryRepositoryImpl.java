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
import javax.sql.DataSource;
import models.LoginHistory;
import repo.IHistoryRepository;


public class HistoryRepositoryImpl implements IHistoryRepository {
    private static final String ID_LOGIN_HISTORY = "IDLoginHistory";
    private static final String LOGIN_DATE = "LoginDate";
    private static final String IP_ADDRESS = "IPAddress";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String EMAIL = "Email";
    
    private static final String SELECT_LOGIN_HISTORY = "{ CALL getLoginHistory () }";
    private static final String CREATE_LOGIN_HISTORY = "{ CALL createLoginHistory (?,?,?,?,?,?) }";

    @Override
    public List<LoginHistory> getLoginHistory() throws Exception {
        List<LoginHistory> history = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_LOGIN_HISTORY);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LoginHistory historyItem = new LoginHistory(
                        rs.getInt(ID_LOGIN_HISTORY), 
                        rs.getString(IP_ADDRESS), 
                        rs.getDate(LOGIN_DATE), 
                        rs.getString(FIRST_NAME), 
                        rs.getString(LAST_NAME), 
                        rs.getString(EMAIL));
                history.add(historyItem);
            }
        }
        return history;
    }

    @Override
    public int createLoginHistory(LoginHistory loginHistory) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_LOGIN_HISTORY)) {

            stmt.setDate(1, loginHistory.getDateTime());
            stmt.setString(2, loginHistory.getIpAddress());
            stmt.setString(3, loginHistory.getFirstName());
            stmt.setString(4, loginHistory.getLastName());
            stmt.setString(5, loginHistory.getEmail());
            stmt.registerOutParameter(6, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(6);
        }
    }
    
}
