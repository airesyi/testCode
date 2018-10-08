package com.test.mapper;

import com.test.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * auth: shi yi
 * create date: 2018/8/31
 */
@Mapper
public interface UserMapper {

   /*   @Select("select * from tb_user")
      @Results({
              @Result(property = "name",column = "uname"),
              @Result(property = "age",column = "age"),
              @Result(property = "gender",column = "gender")
      })*/
    List<User> findUser(String id);
}
