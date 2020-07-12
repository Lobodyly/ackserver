package com.manji.ackservice.mapper.kcumapper;

import com.manji.ackservice.MybatisUtil.SimpleInsertLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleSelectLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleUpdateLangDriver;
import com.manji.ackservice.model.kcumodel.KcuFeedback;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/25
 * Time:9:51
 */
@Mapper
public interface KcuFeedbackMapper {

    /**
    * @Description: 新增反馈
    * @Param: [kcuFeedback]
    * @return: void
    * @Author: LuoYu
    * @Date: 2018/7/25
    */
    @Insert("insert into kcu_feedback (#{kcuFeedback})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    boolean addKcuFeedback(KcuFeedback kcuFeedback);
    
    /** 
    * @Description: 修改反馈 
    * @Param: [kcuFeedback] 
    * @return: boolean 
    * @Author: LuoYu 
    * @Date: 2018/7/31 
    */ 
    @Update("update kcu_feedback (#{kcuFeedback}) where id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateKcuFeedback(KcuFeedback kcuFeedback);
    
    /** 
    * @Description: 根据条件查询反馈
    * @Param: [kcuFeedback] 
    * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuFeedback> 
    * @Author: LuoYu 
    * @Date: 2018/7/25 
    */ 
    @Select("<script> " +
            "select * from kcu_feedback " +
            " <where> "+
            "<if test=\"system_code !=null and system_code !=''\"> and system_code = #{system_code} </if> "+
            "<if test=\"kcuinformation_title !=null and kcuinformation_title != ''\"> and kcuinformation_title like '%${kcuinformation_title}%' </if> "+
            "<if test=\"state!=null\"> and state = #{state} </if>"+
            "<if test=\"feedback_content != null and feedback_content !=''\"> and feedback_content = #{feedback_content} </if> "+
            "<if test=\"start_time != null and start_time != ''\"> and add_time &gt; #{start_time} </if> "+
            "<if test=\"end_time != null and end_time != ''\"> and add_time &lt; #{end_time} </if> "+
            "<if test=\"kcuinformation_id!=null\"> and kcuinformation_id = #{kcuinformation_id} </if> "+
            " </where> " +
            "</script>")
    List<KcuFeedback> listKcuFeedback(@Param("kcuinformation_title") String kcuinformation_title,@Param("state") Integer state,@Param("system_code") String system_code,@Param("feedback_content") String feedback_content,@Param("start_time") String start_time,@Param("end_time") String end_time,@Param("kcuinformation_id") Integer kcuinformation_id);



    /**
    * @Description: 根据用户id和知识点id查询反馈状态
    * @Param: [user_id, kcuinformation_id]
    * @return: com.manji.ackservice.model.kcumodel.KcuFeedback
    * @Author: LuoYu
    * @Date: 2018/7/31
    */
    @Select("select state,id from kcu_feedback where user_id = #{user_id} and kcuinformation_id = #{kcuinformation_id}")
    KcuFeedback getKcuFeedback(@Param("user_id") Integer user_id,@Param("kcuinformation_id") Integer kcuinformation_id);
}
