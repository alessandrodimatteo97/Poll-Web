/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.data;

import java.sql.Connection;

/**
 *
 * @author davide
 */
public class DAO {
    
    protected final DataLayer dataLayer;
    protected final Connection connection;

    public DAO(DataLayer d) {
        this.dataLayer = d;
        this.connection = d.getConnection();
    }

    protected DataLayer getDataLayer() {
        return dataLayer;
    }

    protected Connection getConnection() {
        return connection;
    }

    public void init() /* throws DataException */{

    }

    public void destroy() /* throws DataException */ {

    }
}
