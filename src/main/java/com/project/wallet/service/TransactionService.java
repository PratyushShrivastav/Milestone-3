package com.project.wallet.service;


import com.project.wallet.entity.TransactionEntity;
import com.project.wallet.entity.UserEntity;
import com.project.wallet.exception.ResourceNotFoundException;
import com.project.wallet.repository.TransactionRepository;
import com.project.wallet.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {


    private static final Logger LOGGER =  LoggerFactory.getLogger(TransactionService.class);





    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TransactionRepository transactionrepository;




    public List<TransactionEntity> getDetails(int userId) {


        UserEntity user= userRepository.findByuserID(userId);
        if(user==null) {

            LOGGER.warn("No user found with this user Id");
            throw new ResourceNotFoundException("No user found with this user Id");
        }

        String phonenumber = user.getPhoneNumber();
        List<TransactionEntity> payeruserdetails =  transactionrepository.findAllBypayerUser(phonenumber);

        List<TransactionEntity> payeeuserdetails =  transactionrepository.findAllBypayeeUser(phonenumber);

        List<TransactionEntity> finaluserdetails = new ArrayList<TransactionEntity>();
        finaluserdetails.addAll(payeruserdetails);
        finaluserdetails.addAll(payeeuserdetails);


        if (finaluserdetails.isEmpty()) {

            LOGGER.warn("No transaction present for the user with userID"+ userId);
            throw new ResourceNotFoundException("No transaction present for the user with userID" + userId);
        }
        return  finaluserdetails;



    }




    public TransactionEntity getTransactionDetails(int transactionId) {

        TransactionEntity transactionDetail= transactionrepository.findBytransactionID(transactionId);
        if(transactionDetail==null) {

            LOGGER.warn("The transactionId does not exist");
            throw new ResourceNotFoundException("The transactionId does not exist");
        }
        return transactionDetail;



    }



    public TransactionEntity getById(int i) {
        return transactionrepository.findBytransactionID(i);
    }

    public Object findTransaction() {
        return transactionrepository.findAll();
    }







}
