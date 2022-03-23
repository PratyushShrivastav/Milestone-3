package com.project.wallet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.wallet.controller.TransactionController;
import com.project.wallet.controller.UserController;
import com.project.wallet.entity.TransactionEntity;
import com.project.wallet.entity.UserEntity;
import com.project.wallet.service.TransactionService;
import com.project.wallet.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TransactionControllerTest {



    @Mock
    private TransactionService transactionService;
    private TransactionEntity transaction;
    private List<TransactionEntity> transactionlist;


    @InjectMocks
    private TransactionController transactionController;



    @Autowired
    private MockMvc mockMvc1;

    @BeforeEach
    public void setUser()
    {
        transaction=new TransactionEntity(1,"9109549374","9988223344",30);
        mockMvc1=MockMvcBuilders.standaloneSetup(transactionController).build();

    }
    @AfterEach
    public void cutUser()
    {
        transaction=null;
    }




    @Test
    public void getTransactionById()throws Exception
    {
        when(transactionService.getTransactionDetails(1)).thenReturn(transaction);
        mockMvc1.perform(MockMvcRequestBuilders.get("/transaction/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(transaction))).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }



    @Test
    public void getAllTransactionForUser()throws Exception
    {

        when(transactionService.getDetails(5)).thenReturn(transactionlist);
        mockMvc1.perform(get("/transaction?userId=5").contentType(MediaType.APPLICATION_JSON).content(asJsonString(transaction))).andExpect(status().isOk());
        verify(transactionService).getDetails(5);
        verify(transactionService,times(1)).getDetails(5);

    }


    public static String asJsonString(final Object obj)
    {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
