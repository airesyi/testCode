package com.test.web;

import com.test.mapper.UserMapper;
import com.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * auth: shi yi
 * create date: 2018/8/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testQuery() throws Exception {
        List<User> users = userMapper.findUser("1");
        System.out.println(users.size());
    }
}
