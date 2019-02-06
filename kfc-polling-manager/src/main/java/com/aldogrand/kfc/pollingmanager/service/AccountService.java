package com.aldogrand.kfc.pollingmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.aldogrand.kfc.pollingmanager.model.Account;
import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.repository.AccountRepository;

public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        Validate.notNull(accountRepository, "AccountRepository cannot be null.");
        this.accountRepository = accountRepository;
    }

    public List<EventAttributes> decorateEventAttributes(EventAttributes eventAttributes) {
        validateAttributes(eventAttributes);

        List<EventAttributes> eventAttributesList = new ArrayList<EventAttributes>();
        Iterable<Account> accounts = getAccounts(eventAttributes);
        for (Account account : accounts) {
            EventAttributes accountEventAttributes = eventAttributes.clone();
            accountEventAttributes.setIntegrationModuleId(account.getConnector().getId().toString());
            accountEventAttributes.setIntegrationModuleName(account.getConnector().getName());
            accountEventAttributes.setUsername(account.getUsername());
            accountEventAttributes.setPassword(account.getPassword());
            eventAttributesList.add(accountEventAttributes);
        }

        return eventAttributesList;
    }

    private Iterable<Account> getAccounts(EventAttributes eventAttributes) {
        Iterable<Account> accounts;
        String integrationModuleName = eventAttributes.getIntegrationModuleName();
        if (StringUtils.isBlank(integrationModuleName)) {
            accounts = accountRepository.findAll();
        } else {
            accounts = accountRepository.findByConnectorName(integrationModuleName);
        }
        return accounts;
    }

    private void validateAttributes(EventAttributes eventAttributes) {
        Validate.notNull(eventAttributes, "EventAttributes cannot be null for AccountService.");
        Validate.notNull(eventAttributes.getRule(), "EventAttributes rule cannot be null for AccountService.");
    }
}
