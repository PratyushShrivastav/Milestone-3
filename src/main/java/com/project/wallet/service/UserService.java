package com.project.wallet.service;



import com.project.wallet.entity.TransactionEntity;
import com.project.wallet.entity.UserEntity;
import com.project.wallet.exception.LackOfResourceException;
import com.project.wallet.exception.ResourceAlreadyExistsException;
import com.project.wallet.exception.ResourceNotFoundException;
import com.project.wallet.repository.TransactionRepository;
import com.project.wallet.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService
 {

    private static final Logger LOGGER =  LoggerFactory.getLogger(UserService.class);





    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TransactionRepository transactionrepository;




    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {


        return new User("admin","password",new ArrayList<>());
    }




    public UserEntity createWallet(String phoneNumber) {



        UserEntity userphonenumber= userRepository.findByphoneNumber(phoneNumber);

        if(userphonenumber!=null) {

            LOGGER.warn("The user already exists");
            throw new ResourceAlreadyExistsException("The user already exists");


        }



        UserEntity user = new UserEntity();
        user.setPhoneNumber(phoneNumber);
        user.setAmount(1000);


        return userRepository.save(user);




    }


    public TransactionEntity doTransaction(TransactionEntity transaction) {


        int amount= transaction.getTransactionAmount();


        String payer = transaction.getPayerUser();
        UserEntity user2 = userRepository.findByphoneNumber(payer);
        if(user2==null) {
            LOGGER.warn("Payer with this phone number does not exist");
            throw new ResourceNotFoundException("Payer with this phone number does not exist");
        }

        if(user2.getAmount()<amount) {
            LOGGER.warn("Payer does not have enough money");
            throw new LackOfResourceException("The payer does not have enough money");
        }
        user2.setAmount(user2.getAmount()-amount);




        String payee = transaction.getPayeeUser();
        UserEntity user1 = userRepository.findByphoneNumber(payee);
        if(user1==null) {

            LOGGER.warn("Payee with this phone number does not exist");
            throw new ResourceNotFoundException("Payee with this phone number does not exist");
        }


        user1.setAmount(user1.getAmount()+amount);



        return transactionrepository.save(transaction);





    }



     public List<UserEntity> findAllUser() {
        return userRepository.findAll();

     }

     public Object findByNumber(String s) {

        return userRepository.findByphoneNumber(s);


     }

     public UserEntity add(UserEntity user) {

        return userRepository.save(user);
     }
//
//     public TransactionEntity getById(int i) {
//        return transactionrepository.findBytransactionID(i);
//     }
//
//     public Object findTransaction() {
//        return transactionrepository.findAll();
//     }
 }
