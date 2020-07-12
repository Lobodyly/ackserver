package com.manji.ackservice.mapper.kcumapper;

import com.manji.ackservice.MybatisUtil.SimpleInsertLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleSelectInLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleSelectLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleUpdateLangDriver;
import com.manji.ackservice.model.kcumodel.KcuFeedbackSet;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/25
 * Time:15:06
 */
public interface KcuFeedbackSetMapper {

    /** 
    * @Description: 新增反馈内容配置 
    * @Param: [kcuFeedbackSet] 
    * @return: boolean 
    * @Author: LuoYu 
    * @Date: 2018/7/25 
    */ 
    @Insert("insert into kcu_feedback_content_set (#{kcuFeedbackSet})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    boolean addKcuFeedbackSet(KcuFeedbackSet kcuFeedbackSet);

    /** 
    * @Description: 修改反馈内容配置
    * @Param: [kcuFeedbackSet] 
    * @return: boolean 
    * @Author: LuoYu 
    * @Date: 2018/7/25 
    */ 
    @Update("update kcu_feedback_content_set (#{kcuFeedbackSet}) where id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateKcuFeedbackset(KcuFeedbackSet kcuFeedbackSet);

    /** 
    * @Description: 根据id删除反馈内容配置
    * @Param: [id] 
    * @return: boolean 
    * @Author: LuoYu 
    * @Date: 2018/7/26 
    */ 
    @Delete("delete from kcu_feedback_content_set where id = #{id}")
    boolean deleteKcuFeedbackset(Integer id);

    /** 
    * @Description: 条件查询反馈内容配置
    * @Param: [kcuFeedbackSet] 
    * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuFeedbackSet> 
    * @Author: LuoYu 
    * @Date: 2018/7/25 
    */ 
    @Select("select * from kcu_feedback_content_set (#{kcuFeedbackSet})")
    @Lang(SimpleSelectLangDriver.class)
    List<KcuFeedbackSet> listKcuFeedbackset(KcuFeedbackSet kcuFeedbackSet);
}
