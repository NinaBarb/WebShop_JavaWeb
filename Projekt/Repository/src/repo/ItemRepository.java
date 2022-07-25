/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repo;

import java.util.List;
import models.Item;

/**
 *
 * @author Nina
 */
public interface ItemRepository {
    
    List<Item> getItems() throws Exception;
}
