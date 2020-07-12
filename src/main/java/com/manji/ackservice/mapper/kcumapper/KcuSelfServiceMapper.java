package com.manji.ackservice.mapper.kcumapper;

import com.manji.ackservice.MybatisUtil.SimpleInsertLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleSelectLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleUpdateLangDriver;
import com.manji.ackservice.model.kcumodel.KcuSelfService;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/31
 * Time:10:09
 */
@Mapper
public interface KcuSelfServiceMapper {


    /**
    * @Description: 添加自助服务配置
    * @Param: [kcuSelfService]
    * @return: boolean
    * @Author: LuoYu
    * @Date: 2018/7/31
    */
    @Insert("insert into kcu_self_service (#{kcuSelfService})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    boolean addKcuSelfService(KcuSelfService kcuSelfService);


    /**
    * @Description: 修改自助服务配置
    * @Param: [kcuSelfService]
    * @return: boolean
    * @Author: LuoYu
    * @Date: 2018/7/31
    */
    @Update("update kcu_self_service (#{kcuSelfService}) where id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateKcuSelfService(KcuSelfService kcuSelfService);


    /** 
    * @Description: 状态删除自助服务配置
    * @Param: [id] 
    * @return: boolean 
    * @Author: LuoYu 
    * @Date: 2018/7/31 
    */
    @Update("update kcu_self_service set is_delete = #{is_delete} where id = #{id}")
    boolean deleteKcuSelfService(@Param("id") Integer id,@Param("is_delete") Integer is_delete);


    /**
    * @Description: 条件查询自助服务配置
    * @Param: [kcuSelfService]
    * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuSelfService>
    * @Author: LuoYu
    * @Date: 2018/7/31
    */
    @Select("select * from kcu_self_service (#{kcuSelfService}) order by sort")
    @Lang(SimpleSelectLangDriver.class)
    List<KcuSelfService> listKcuSelfService(KcuSelfService kcuSelfService);


}
