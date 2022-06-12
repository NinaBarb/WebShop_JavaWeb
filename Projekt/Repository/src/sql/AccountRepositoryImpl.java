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
import java.util.Optional;
import javax.sql.DataSource;
import models.UserAccount;
import repo.IAccountRepository;


public class AccountRepositoryImpl implements IAccountRepository {
    
    private static final String ID_USER_ACCOUNT = "IDUserAccount";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "Pswd";
    private static final String IS_ADMIN = "IsAdmin";

    private static final String CREATE_ACCOUNT = "{ CALL creatAccount (?,?,?,?,?,?) }";
    private static final String EXISTS = "{ CALL doesAccountExist (?) }";
    private static final String GET_ACCOUNT = "{ CALL getAccount (?,?) }";
    
    @Override
    public int createAccount(UserAccount account) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ACCOUNT)) {

            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getFirstName());
            stmt.setString(3, account.getLastName());
            stmt.setString(4, account.getPassword());
            stmt.setBoolean(5, account.isIsAdmin());
            stmt.registerOutParameter(6, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(6);
        }
    }

    @Override
    public boolean doesAccountExist(String email) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(EXISTS)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Optional<UserAccount> getAccount(String email, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_ACCOUNT)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new UserAccount(
                            rs.getInt(ID_USER_ACCOUNT),
                            rs.getString(FIRST_NAME),
                            rs.getString(LAST_NAME),
                            rs.getString(EMAIL),
                            rs.getString(PASSWORD),
                            rs.getBoolean(IS_ADMIN)));
                }
            }
        }
        return Optional.empty();
    }
    
}
