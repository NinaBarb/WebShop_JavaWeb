/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import enums.PaymentType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import models.LoginHistory;
import models.PaymentHistory;
import models.UserAccount;
import repo.IHistoryRepository;


public class HistoryRepositoryImpl implements IHistoryRepository {
    private static final String ID_LOGIN_HISTORY = "IDLoginHistory";
    private static final String LOGIN_DATE = "LoginDate";
    private static final String IP_ADDRESS = "IPAddress";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String EMAIL = "Email";
    
    private static final String ID_PAYMENT_HISTORY = "IDPaymentHistory";
    private static final String PAYMENT_TYPE = "PaymentType";
    private static final String USER_ACCOUNT_ID = "UserAccountID";
    private static final String ITEMS = "Items";
    
    private static final String SELECT_LOGIN_HISTORY = "{ CALL getLoginHistory () }";
    private static final String CREATE_LOGIN_HISTORY = "{ CALL createLoginHistory (?,?,?,?,?,?) }";
    
    private static final String SELECT_PAYMENT_HISTORY_ADMIN = "{ CALL getPaymentHistoryADMIN () }";
    private static final String SELECT_PAYMENT_HISTORY_USER = "{ CALL getPaymentHistoryUSER (?) }";
    private static final String CREATE_PAYMENT_HISTORY = "{ CALL createPaymentHistory (?,?,?,?) }";

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

    @Override
    public int createPaymentHistory(PaymentHistory paymentHistory) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_PAYMENT_HISTORY)) {

            stmt.setObject(1, paymentHistory.getPaymentType().toString(), JDBCType.NVARCHAR);
            stmt.setInt(2, paymentHistory.getPayer());
            stmt.setString(3, paymentHistory.getItems());
            stmt.registerOutParameter(4, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(4);
        }
    }

    @Override
    public List<PaymentHistory> getPaymentHistory_ADMIN() throws Exception {
        List<PaymentHistory> history = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PAYMENT_HISTORY_ADMIN);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PaymentHistory historyItem = new PaymentHistory(
                        rs.getInt(ID_PAYMENT_HISTORY), 
                        PaymentType.valueOf(rs.getString(PAYMENT_TYPE)), 
                        rs.getInt(USER_ACCOUNT_ID), 
                        rs.getString(ITEMS));
                history.add(historyItem);
            }
        }
        return history;
    }

    @Override
    public List<PaymentHistory> getPaymentHistory_USER(int idUser) throws Exception {
        List<PaymentHistory> payments  = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PAYMENT_HISTORY_USER)) {
            stmt.setInt(1, idUser);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(new PaymentHistory(
                        rs.getInt(ID_PAYMENT_HISTORY),
                        PaymentType.valueOf(rs.getString(PAYMENT_TYPE)),
                        rs.getInt(USER_ACCOUNT_ID),
                        rs.getString(ITEMS)));
                }
            }

            return payments;
        }
    }
    
}
