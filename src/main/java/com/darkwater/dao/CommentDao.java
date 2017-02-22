package com.darkwater.dao;

import com.darkwater.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo1 on 2017/1/25.
 */

@Repository
@Mapper
public interface CommentDao {

    String TABLE_NAME = " comment ";
    String INSERT_FIELD = "  content,user_id,entity_id,entity_type,created_date,status ";
    String SELECT_FIELD = " id, " + INSERT_FIELD;

    //通过实体（问题）查找其对应的评论
    List<Comment> selectLatestCommentsByEntityId(@Param("entityId") int entityId,
                                                 @Param("entityType") int entityType,
                                                 @Param("offset") int offset,
                                                 @Param("limit") int limit);

    //增加评论
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELD, ") values(#{comtent},#{userId},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    //通过实体（问题）查找评论数量
    @Select({"select count(id) from ", TABLE_NAME, " where entity_id = #{entityId} and entity_type = #{entityType} order by desc"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    //通过ID查找评论
    @Select({"select ", SELECT_FIELD, " from ", TABLE_NAME, " where id = #{id} "})
    Comment selectById(int id);

    //通过用户ID找评论
    @Select({"select ", SELECT_FIELD, " from ", TABLE_NAME, " where user_id = #{userId} order by desc"})
    List<Comment> selectByUserId(int userId);

    //更新评论的内容
    @Update({"update ", TABLE_NAME, " set content = #{content} where id = #{id}"})
    void updateContent(Comment comment);

    //直接删除评论
    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    void deleteById(int id);

    //更新评论的状态（是否删除，是否显示）
    @Update({"update ", TABLE_NAME, " set status = #{status} where id = #{id}"})
    int updateStatus(int id, int status);

//    @Select({"select ", SELECT_FIELD, " from ", TABLE_NAME, " where entity_id = #{entityId}"})
//    Comment selectByEntityId(int entityId);


}
