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
import repo.AccountRepository;


public class SqlAccountRepository implements AccountRepository {

    private static final String ID_USER_ACCOUNT = "IDUserAccount";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Pswd";

    private static final String CREATE_ACCOUNT = "{ CALL createUserAccount (?,?,?) }";
    private static final String EXISTS = "{ CALL doesUserExist (?) }";
    
    @Override
    public int createAccount(UserAccount account) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ACCOUNT)) {

            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.registerOutParameter(3, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }

    @Override
    public boolean doesAccountExist(String userName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(EXISTS)) {

            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Optional<UserAccount> selectAccount(String userName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(EXISTS)) {

            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new UserAccount(
                            rs.getInt(ID_USER_ACCOUNT),
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD)));
                }
            }
        }
        return Optional.empty();
    }
    
}
