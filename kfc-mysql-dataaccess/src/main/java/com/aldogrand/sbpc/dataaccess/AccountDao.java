package com.aldogrand.sbpc.dataaccess;

import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.AccountDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;

/**
 * <p>
 * <b>Title</b> AccountDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link AccountDto}s.
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
public interface AccountDao
{
    /**
     * Get the {@link AccountDto} for the given accountId
     * @param id
     * @return the account
     */
    AccountDto getAccount(Long id);

    /**
     * Get the {@link AccountDto} from the connector with the username.
     * @param connectorId
     * @param username
     * @return
     */
    AccountDto getAccount(Long connectorId, String username);
    
    /**
     * Get the {@link AccountDto}s
     * @param offset
     * @param maxResults
     * @return
     */
    List<AccountDto> getAccounts(Integer offset, Integer maxResults);
    
    /**
     * Get the {@link AccountDto}s from the interested clients table for the {@link MarketDto}
     */
    List<AccountDto> getAccountsFromInterestedClients(MarketDto market, Integer offset, Integer maxResults);

    
	/**
	 * Save the {@link AccountDto} and return the updated version.
	 * @param account
	 * @return
	 */
	AccountDto createAccount(AccountDto account);

	/**
	 * Delete the account with given accountId
	 * @param accountId
	 */
	void deleteAccount(Long accountId);

	/**
	 * Delete the accounts with given accountIds
	 * @param accountIds
	 */
	void deleteAccounts(List<Long> accountIds);

	/**
	 * Update the {@link AccountDto} and return the updated version.
	 * @param account
	 * @return
	 */
	AccountDto updateAccount(AccountDto account);

	/**
	 * Delete the {@link AccountDto}.
	 * @param account
	 */
	void deleteAccount(AccountDto account);

	/**
	 * Return all the accounts for the connectorId
	 * @param connectorId
	 * @param offset
	 * @param maxResults
	 * @return
	 */
	List<AccountDto> getAccounts(Long connectorId, Integer offset, Integer maxResults);
}
