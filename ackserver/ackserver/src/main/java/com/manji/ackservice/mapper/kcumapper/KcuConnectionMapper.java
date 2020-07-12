package com.manji.ackservice.mapper.kcumapper;

import com.manji.ackservice.MybatisUtil.SimpleSelectLangDriver;
import com.manji.ackservice.model.kcumodel.KcuCASConnection;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/24
 * Time:11:49
 */
@Mapper
public interface KcuConnectionMapper {

    /**
    * @Description: 添加系统code和分类id的连接
    * @Param: [kcucategory_id, kcusystem_code]
    * @return: boolean
    * @Author: LuoYu
    * @Date: 2018/7/24
    */
    @Insert("insert into kcu_system_category (kcucategory_id,kcusystem_code) value (#{kcucategory_id},#{kcusystem_code})")
    boolean addSysAndCateConnetion (@Param("kcucategory_id") Integer kcucategory_id,@Param("kcusystem_code") String kcusystem_code);

    /**
    * @Description: 根据系统code和分类id,删除连接
    * @Param: [kcuinformation_id, kcucategory_id]
    * @return: boolean
    * @Author: LuoYu
    * @Date: 2018/7/24
    */
    @Delete("delete from kcu_system_category where kcucategory_id = #{kcucategory_id} and kcusystem_code = #{kcusystem_code}")
    boolean deleteSysAndCateConnetion (@Param("kcucategory_id") Integer kcucategory_id,@Param("kcusystem_code") String kcusystem_code);

    /**
    * @Description:  根据系统id,删除连接
    * @Param: [kcuinformation_id]
    * @return: boolean
    * @Author: LuoYu
    * @Date: 2018/7/24
    */
    @Delete("delete from kcu_system_category where kcusystem_code = #{kcusystem_code}")
    boolean deleteByKcuSystemCode (@Param("kcusystem_code") String kcusystem_code);

    /**
     * @Description:  根据分类id,删除连接
     * @Param: [kcucategory_id]
     * @return: boolean
     * @Author: LuoYu
     * @Date: 2018/7/24
     */
    @Delete("delete from kcu_system_category where kcucategory_id = #{kcucategory_id}")
    boolean deleteByKcucategoryId (@Param("kcucategory_id") Integer kcucategory_id);
    
    /**
    * @Description: 条件查询对应数据
    * @Param: [kcuCASConnection]
    * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuCASConnection>
    * @Author: LuoYu
    * @Date: 2018/7/24
    */
    @Select("select * from kcu_system_category (#{kcuCASConnection})")
    @Lang(SimpleSelectLangDriver.class)
    List<KcuCASConnection> listCAIConnection (KcuCASConnection kcuCASConnection);


    /**
    * @Description: 根据二级分类查询该分类所属的一级分类的系统code
    * @Param: [id]
    * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuCASConnection>
    * @Author: LuoYu
    * @Date: 2018/8/6
    */
    @Select("select * from kcu_system_category ksc " +
            "inner join kcu_category kc on ksc.kcucategory_id = kc.id " +
            "inner join kcu_category kc1 on kc1.pid = kc.id and kc1.id = #{id} "
    )
    List<KcuCASConnection> listKcuCASConnectionByKcuCategoryId(@Param("id") Integer id);
}
