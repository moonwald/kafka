package com.aldogrand.sbpc.dataaccess.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.aldogrand.sbpc.connectors.model.Account;



/**
 * <p>
 * <b>Title</b> ConnectorModelFactory
 * </p>
 * <p>
 * <b>Description</b> A factory class that creates model objects that can be used
 * by {@link com.aldogrand.sbpc.connectors.model.Connector}s from their dataaccess DTO equivalent representations.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
public class ConnectorModelFactory
{
    private static final Logger logger = LogManager.getLogger(ModelFactory.class);
    
    /**
     * Create an {@link Account} from an {@link AccountDto}.
     * @param account
     * @return
     */
    public static Account createAccount(AccountDto account)
    {
        assert account != null;
        
        logger.debug("Creating connector account from dto " + account.getUsername());
        
        Account a = new Account();
        a.setUsername(account.getUsername());
        a.setPassword(account.getPassword());
        a.setCurrency(account.getCurrency());
        for (AccountPropertyDto prop : account.getAccountProperties())
        {
            a.setProperty(prop.getName(), prop.getValue());
        }
        return a;
    }
   
    /**
     * Prevent instantiation.
     */
    private ConnectorModelFactory()
    {
    }
}
