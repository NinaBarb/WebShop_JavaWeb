/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import models.Item;
import repo.ItemRepository;


public class SqlItemRepository implements ItemRepository {
    
    private static final String ID_ITEM = "IDItem";
    private static final String TITLE = "title";
    private static final String PRICE = "Price";
    private static final String DESCRIPTION = "Descr";

    private static final String GET_ITEMS = "{ CALL getItems}";
    
    @Override
    public List<Item> getItems() throws Exception {
        List<Item> items = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_ITEMS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item(
                        rs.getInt(ID_ITEM),
                        rs.getString(TITLE),
                        rs.getInt(PRICE),
                        rs.getString(DESCRIPTION));

                items.add(item);
            }
        }
        return items;
    }
    
}
