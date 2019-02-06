package com.aldogrand.kfc.pollingmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.aldogrand.kfc.pollingmanager.model.Account;
import com.aldogrand.kfc.pollingmanager.model.Connector;
import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.model.EventAttributesMother;
import com.aldogrand.kfc.pollingmanager.repository.AccountRepository;

public class AccountServiceTest {

    private AccountService accountService;
    private AccountRepository accountRepository;

    @Before
    public void setup() {
        accountRepository = Mockito.mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test(expected = NullPointerException.class)
    public void accountRepositoryCannotBeNull() {
        new AccountService(null);
    }

    @Test(expected = NullPointerException.class)
    public void eventAttributesCannotBeNull() {
        accountService.decorateEventAttributes(null);
    }

    @Test(expected = NullPointerException.class)
    public void eventAttributesRuleCannotBeNull() {
        // Given
        EventAttributes eventAttributes = EventAttributesMother.createMatchbookAllEventsAttributes();
        eventAttributes.setRule(null);

        // When
        accountService.decorateEventAttributes(eventAttributes);
    }

    @Test
    public void decorateEventAttributes() {
        // Given
        EventAttributes eventAttributes = EventAttributesMother.createMatchbookAllEventsAttributes();
        List<Account> accounts = createTestAccounts(eventAttributes);
        Mockito.when(accountRepository.findByConnectorName(eventAttributes.getIntegrationModuleName())).thenReturn(accounts);

        // When
        List<EventAttributes> eventAttributesList = accountService.decorateEventAttributes(eventAttributes);

        // Then
        Assert.assertNotNull(eventAttributesList);
        Assert.assertEquals(1, eventAttributesList.size());
        Assert.assertEquals(eventAttributes.getRule(), eventAttributesList.get(0).getRule());
        Mockito.verify(accountRepository).findByConnectorName(eventAttributes.getIntegrationModuleName());
    }
    
    @Test
    public void decorateEventAttributesWhenNoModuleNameSet() {
        // Given
        EventAttributes eventAttributes = EventAttributesMother.createMatchbookAllEventsAttributes();
        eventAttributes.setIntegrationModuleName(null);
        List<Account> accounts = createTestAccounts(eventAttributes);
        Mockito.when(accountRepository.findAll()).thenReturn(accounts);

        // When
        List<EventAttributes> eventAttributesList = accountService.decorateEventAttributes(eventAttributes);

        // Then
        Assert.assertNotNull(eventAttributesList);
        Assert.assertEquals(1, eventAttributesList.size());
        Assert.assertEquals(eventAttributes.getRule(), eventAttributesList.get(0).getRule());
        Mockito.verify(accountRepository).findAll();
    }

    private List<Account> createTestAccounts(EventAttributes eventAttributes) {
        List<Account> accounts = new ArrayList<>();
        Account account = createTestAccount(eventAttributes);
        accounts.add(account);
        return accounts;
    }

    private Account createTestAccount(EventAttributes eventAttributes) {
        Account account = new Account();
        account.setId(1L);
        account.setPassword("password");
        account.setUsername("username");
        account.setCurrency("GBP");
        Connector connector = new Connector();
        connector.setId(1L);
        connector.setName(eventAttributes.getIntegrationModuleName());
        account.setConnector(connector);
        return account;
    }
}
