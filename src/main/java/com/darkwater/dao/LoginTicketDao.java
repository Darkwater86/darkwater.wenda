package com.darkwater.dao;

import com.darkwater.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo1 on 2017/2/5.
 */
@Repository
@Mapper
public interface LoginTicketDao {
    String TABLE_NAME = "  login_ticket  ";
    String INSERT_FIELDS = "  user_id,empired,status,ticket  ";
    String SELECT_FIELDS = "  id, "+INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values(#{userId},#{empired},#{status},#{ticket})" })
    int addTicket(LoginTicket ticket);

    @Select({" select ",SELECT_FIELDS," from ",TABLE_NAME," where ticket = #{ticket}"})
    LoginTicket selectTicket(String ticket);

    @Update({"update" ,TABLE_NAME,"set status = #{status} where ticket = #{ticket}"})
    void updateStatus(@Param("status")int status,@Param("ticket")String ticket);
}
