package com.darkwater.dao;

import com.darkwater.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo1 on 2017/1/25.
 */

@Repository
@Mapper
public interface QuestionDao {

    String TABLE_NAME = " question ";
    String INSERT_FIELD = " title,content,created_date,user_id,comment_count ";
    String SELECT_FIELD = " id, "+INSERT_FIELD;


    List<Question> selectLatestQuestions(@Param("userId")int userId,
                                        @Param("offset")int offset,
                                        @Param("limit")int limit);

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELD,") values(#{title},#{content},#{createdDate},#{userId},#{commentCount})" })
    int addQuestion(Question question);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where id = #{id}"})
    Question selectById(int Id);


    @Update({"update ",TABLE_NAME," set comment_count = #{commentCount} where id = #{id}"})
    int updateCommentCount(int id,int commentCount);

    @Update({"update ",TABLE_NAME," set content = #{content} where id = #{id}"})
    void updateContent(Question question);


    @Delete({"delete from ",TABLE_NAME," where id = #{id}"})
    void deleteById(int id);

}
