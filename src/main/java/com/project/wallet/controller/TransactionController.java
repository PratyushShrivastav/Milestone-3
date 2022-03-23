package com.project.wallet.controller;


import com.project.wallet.entity.TransactionEntity;
import com.project.wallet.exception.ResourceNotFoundException;
import com.project.wallet.service.TransactionService;
import com.project.wallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {




    @Autowired
    private UserService userservice;


    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity getTransactionIdDetails(@PathVariable int transactionId)  {


        try {
            TransactionEntity transaction= transactionService.getTransactionDetails(transactionId);
            return new ResponseEntity(transaction, HttpStatus.OK);
        }
        catch(ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }





    @GetMapping("/transaction")
    public ResponseEntity getTransactionDetails(@RequestParam int userId)  {


        try {
            List<TransactionEntity> finallist = transactionService.getDetails(userId);
            return new ResponseEntity(finallist,HttpStatus.OK);
        }
        catch(ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }




}
