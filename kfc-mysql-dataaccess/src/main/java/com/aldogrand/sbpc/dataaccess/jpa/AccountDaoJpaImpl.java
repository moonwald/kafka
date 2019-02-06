package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.ArrayList;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.AccountDao;
import com.aldogrand.sbpc.dataaccess.model.AccountDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> AccountDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> A JPA implementation of {@link AccountDao}.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * 
 * @author Aldo Grand
 * @version 1.0
 */
public class AccountDaoJpaImpl extends GenericJpaDao<AccountDto> implements
        AccountDao
{
    /**
     * Create a new {@link AccountDaoJpaImpl}.
     */
    public AccountDaoJpaImpl()
    {
        super(AccountDto.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.aldogrand.sbpc.dataaccess.AccountDao#getAccount(java.lang
     * .Long)
     */
    @Override
    public AccountDto getAccount(Long id)
    {
        return getEntity(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aldogrand.sbpc.dataaccess.AccountDao#getAccount(
     * java.lang.Long, java.lang.String)
     */
    @Override
    public AccountDto getAccount(Long connectorId, String username)
    {
        assert connectorId != null;
        assert username != null;

        return executeQuery("from Account a where a.connector.id = ?1 and a.username = ?2",
                connectorId, username);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aldogrand.sbpc.dataaccess.AccountDao#getAccounts(
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<AccountDto> getAccounts(Integer offset, Integer maxResults)
    {
        return executeQuery("from Account a order by a.username asc",
                offset, maxResults);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.aldogrand.sbpc.dataaccess.AccountDao#getAccounts(
     * java.lang.Long)
     */
    @Override
    public List<AccountDto> getAccountsFromInterestedClients(MarketDto marketDto, Integer offset, Integer maxResults)
    {   
        StringBuilder queryStr = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        queryStr.append("select i.account from InterestedClient i");
        boolean where = false;
        int i = 1;
        if (marketDto != null)
        {
            queryStr.append(where ? " and" : " where");
            where = true;
            queryStr.append(" i.market = ?");
            queryStr.append(i);
            params.add(marketDto);
            i ++;
        }
        
        if (params.isEmpty())
        {
            return executeQuery(queryStr.toString(), offset, maxResults);
        }
        else
        {
            return executeQuery(queryStr.toString(), offset, maxResults, 
                    params.toArray(new Object[params.size()]));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aldogrand.sbpc.dataaccess.AccountDao#saveAccount(com.
     * aldogrand.sbpc.dataaccess.model.AccountDto)
     */
    @Override
    public AccountDto createAccount(AccountDto account)
    {
        return saveEntity(account);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aldogrand.sbpc.dataaccess.AccountDao#saveAccount(com.
     * aldogrand.sbpc.dataaccess.model.AccountDto)
     */
    @Override
    public AccountDto updateAccount(AccountDto account)
    {
        return saveEntity(account);
    }

    @Override
    public void deleteAccount(Long accountId) {
        AccountDto accountDto = getEntity(accountId);

        if (accountDto != null) {
            removeEntity(accountDto);
        }
    }

    @Override
    public void deleteAccounts(List<Long> accountIds) {
        for (Long accountId : accountIds) {
            deleteAccount(accountId);
        }
    }

    /*
     * 
     * 
     * @see com.aldogrand.sbpc.dataaccess.AccountDao#deleteAccount(
     * com.aldogrand.sbpc.dataaccess.model.AccountDto)
     */
    @Override
    public void deleteAccount(AccountDto account)
    {
        assert account != null;

        removeEntity(account);
    }

    /**
     * 
     * @param connectorId
     * @param offset
     * @param maxResults
     * @see com.aldogrand.sbpc.dataaccess.AccountDao#getAccounts(java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
	@Override
	public List<AccountDto> getAccounts(Long connectorId, Integer offset, Integer maxResults) {
		assert connectorId != null;

		return
			executeQuery(
				"from Account a where a.connector.id = ?1",
				offset,
				maxResults,
				new Object[]{
					connectorId
				}
			);
	}
}
