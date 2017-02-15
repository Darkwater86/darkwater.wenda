package com.darkwater.dao;

import com.darkwater.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo1 on 2017/1/25.
 */


@Repository
@Mapper
public interface UserDao {
    String TABLE_NAME = " user ";
    String INSERT_FIELD = " name,password,salt,head_url ";
    String SELECT_FIELD = " id, "+INSERT_FIELD;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELD,") values(#{name},#{password},#{salt},#{headUrl})" })
    int addUser(User user);


    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where id = #{id}"})
    User selectById(int id);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where name = #{name}"})
    User selectByName(String name);


    @Update({"update ",TABLE_NAME," set password = #{password} where id = #{id}"})
    void updatePassword(User user);


    @Delete({"delete from ",TABLE_NAME," where id = #{id}"})
    void deleteById(int id);

}
