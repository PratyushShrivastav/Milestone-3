package com.project.wallet;


import com.project.wallet.entity.TransactionEntity;
import com.project.wallet.entity.UserEntity;
import com.project.wallet.repository.TransactionRepository;
import com.project.wallet.repository.UserRepository;
import com.project.wallet.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.JsonPathAssertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {



//    @MockBean
//    TransactionRepository transactionRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;



    @Test
    public void getUserTest()
    {
        when(userRepository.findAll()).thenReturn((List<UserEntity>) Stream.of(new UserEntity(1,
                "9876654",
                (int) 30.0)).collect(Collectors.toList()));

        assertEquals(1,userService.findAllUser().size());

    }
    @Test
    public void getUserByPhoneTest()
    {
        UserEntity user=new UserEntity();

        user.setPhoneNumber("15616519");
        user.setAmount(70);
        when(userRepository.findByphoneNumber("15616519")).thenReturn(user);
        Assertions.assertThat(userService.findByNumber("15616519")).isEqualTo(user);
    }


    @Test
    public void addUserTest()
    {
        UserEntity user=new UserEntity(2,"98765432",150);
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user,userService.add(user));
    }




}
