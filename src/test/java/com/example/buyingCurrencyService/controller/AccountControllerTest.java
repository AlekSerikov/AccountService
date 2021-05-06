package com.example.buyingCurrencyService.controller;

import com.example.buyingCurrencyService.model.Currency;
import com.example.buyingCurrencyService.model.entity.Account;
import com.example.buyingCurrencyService.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {AccountController.class})
@WebMvcTest(controllers = {AccountController.class})
@ExtendWith(SpringExtension.class)

public class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private Authentication authentication;

    @Autowired
    private AccountController accountController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getAccount_success_test() {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                List.of(new Currency("USD", 100.0)));
        when(accountService.getAccount(any())).thenReturn(account);

        when(authentication.getName()).thenReturn("admin");

        Account receivedAccount = accountController.getAccount(authentication);
        verify(accountService).getAccount("admin");

        assertEquals(account, receivedAccount);
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    void getAccountHttpRequestAndResponse_success_test() throws Exception {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                List.of(new Currency("USD", 100.0),
                        new Currency("EUR", 100.0)));
        when(accountService.getAccount(any())).thenReturn(account);

        mockMvc.perform(
                get("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencies.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance.name").value("BYN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance.value").value(1000.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencies[0].name").value("USD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencies[0].value").value(100.0));
    }

    @Test
    void getAccountWithParticularCurrency_success_test() {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                List.of(new Currency("USD", 100.0)));
        when(accountService.getAccountWithParticularCurrency("admin", "USD")).thenReturn(account);

        when(authentication.getName()).thenReturn("admin");

        Account receivedAccount = accountController.getAccountWithParticularCurrency("USD", authentication);
        verify(accountService).getAccountWithParticularCurrency("admin", "USD");

        assertEquals(account, receivedAccount);
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    void getAccountWithParticularCurrencyRequestAndResponse_success_test() throws Exception {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                List.of(new Currency("USD", 100.0)));
        when(accountService.getAccountWithParticularCurrency(any(), any())).thenReturn(account);

        when(authentication.getName()).thenReturn("admin");

        Account receivedAccount = accountController.getAccountWithParticularCurrency("USD", authentication);

        mockMvc.perform(get("/api/account/USD", "USD"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(receivedAccount)));
    }


    @Test
    void updateAccount_success_test() {
        Account account = new Account("admin", new Currency("BYN", 1000.0),
                Arrays.asList(new Currency("USD", 100.0)));
        when(accountService.updateAccount(any(), any())).thenReturn(account);

        when(authentication.getName()).thenReturn("admin");

        Currency testCurrency = new Currency("USD", 10);

        Account receivedAccount = accountController.updateAccount(testCurrency, authentication);
        verify(accountService).updateAccount("admin", testCurrency);

        assertEquals(account, receivedAccount);
    }

    @Test
    @Disabled
    @WithMockUser(username = "admin", password = "test", roles = {"ADMIN"})
    void updateAccountHttpRequestAndResponse_success_test() throws Exception {

        Account account = new Account("admin", new Currency("BYN", 1000.0),
                Arrays.asList(new Currency("USD", 100.0)));
        when(accountService.updateAccount(any(), any())).thenReturn(account);

        Currency currency = new Currency("EUR", 100);

//        this.mockMvc
//                .perform(
//                        put("/api/account")
//                                .with(SecurityMockMvcRequestPostProcessors.user("duke").password("pass").roles("ADMIN"))
//                                .with(SecurityMockMvcRequestPostProcessors.authentication())
//                )
//                .andExpect(status().isOk());


        mockMvc.perform(
                put("/api/account")
                        .content(objectMapper.writeValueAsString(currency)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance.name").value("BYN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance.value").value(1000.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencies[0].name").value("USD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencies[0].value").value(100.0));
    }
}