package com.manji.ackservice.mapper.kcumapper;


import com.manji.ackservice.MybatisUtil.SimpleInsertLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleSelectLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleUpdateLangDriver;
import com.manji.ackservice.model.kcumodel.KcuCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KcuCategoryMapper {


    /**
    * @Description: 添加分类
    * @Param: [kcuCategory]
    * @return: boolean
    * @Author: LuoYu
    * @Date: 2018/7/23
    */
    @Insert("insert into kcu_category (#{kcuCategory})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    boolean addKcuCategory(KcuCategory kcuCategory);

    /**
    * @Description: 修改分类
    * @Param: [kcuCategory]
    * @return: boolean
    * @Author: LuoYu
    * @Date: 2018/7/23
    */
    @Update("update kcu_category (#{kcuCategory}) where id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateKcuCategory(KcuCategory kcuCategory);

    /** 
    * @Description: 删除分类 
    * @Param: [id] 
    * @return: boolean 
    * @Author: LuoYu 
    * @Date: 2018/7/23 
    */ 
    @Delete("delete from kcu_category where id = #{id}")
    boolean deleteKcuCategory(@Param("id") Integer id);


    /** 
    * @Description: 根据pid和系统code查询一级分类
    * @Param: [pid, system_code] 
    * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuCategory> 
    * @Author: LuoYu 
    * @Date: 2018/7/23 
    */ 
    @Select("SELECT kcu_category.* FROM kcu_category INNER JOIN kcu_system_category " +
            "ON kcu_category.id = kcu_system_category.kcucategory_id" +
            " WHERE kcu_system_category.kcusystem_code = #{system_code} " +
            "AND kcu_category.pid = #{pid} ORDER BY kcu_category.sort,kcu_category.id")
    List<KcuCategory> listKcuCategoryByPidAndSystemCode(@Param("pid") Integer pid,@Param("system_code") String system_code);


    /**
     * @Description: 根据pid查询二级分类
     * @Param: [pid, system_code]
     * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuCategory>
     * @Author: LuoYu
     * @Date: 2018/7/23
     */
    @Select("SELECT kcu_category.* FROM kcu_category where kcu_category.pid = #{pid} ORDER BY kcu_category.sort,kcu_category.id")
    List<KcuCategory> listKcuCategoryByPid(@Param("pid") Integer pid);

    /** 
    * @Description: 根据条件查询分类 
    * @Param: [kcuCategory] 
    * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuCategory> 
    * @Author: LuoYu 
    * @Date: 2018/7/23 
    */ 
    @Select("select * from kcu_category (#{kcu_category})")
    @Lang(SimpleSelectLangDriver.class)
    List<KcuCategory> listKcuCategory(KcuCategory kcuCategory);

    /**
    * @Description: 根据分类id查询该分类详情
    * @Param: [id]
    * @return: com.manji.ackservice.model.kcumodel.KcuCategory
    * @Author: LuoYu
    * @Date: 2018/7/25
    */
    @Select("select * from kcu_category where id = #{id}")
    KcuCategory getKcuCategory(Integer id);


    /**
    * @Description: 首页热门一级目录查询
    * @Param: [pid, system_code, type]
    * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuCategory>
    * @Author: LuoYu
    * @Date: 2018/7/27
    */
    @Select("SELECT kcu_category.id,kcu_category.title FROM kcu_category INNER JOIN kcu_system_category " +
            "ON kcu_category.id = kcu_system_category.kcucategory_id" +
            " WHERE kcu_system_category.kcusystem_code = #{system_code} " +
            "AND kcu_category.pid = #{pid} AND kcu_category.type = #{type} ORDER BY kcu_category.sort,kcu_category.id")
    List<KcuCategory> listHotCategory(@Param("pid") Integer pid,@Param("system_code") String system_code,@Param("type") Integer type);


    /**
     * @Description: 根据条件查询一级分类
     * @Param: [pid, system_code]
     * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuCategory>
     * @Author: LuoYu
     * @Date: 2018/7/23
     */
    @Select("<script> "+"SELECT kcu_category.* FROM kcu_category INNER JOIN kcu_system_category " +
            "ON kcu_category.id = kcu_system_category.kcucategory_id" +
            "<where>"+
            "<if test=\"kcusystem_code!=null and kcusystem_code!=''\"> and kcu_system_category.kcusystem_code = #{kcusystem_code}  </if> "+
            "<if test=\"title!=null and title!=''\"> and kcu_category.title like '%${title}%' </if> "+
            "<if test=\"pid!=null\"> and kcu_category.pid = #{pid} </if>"+
            "<if test=\"type!=null\"> and kcu_category.type = #{type} </if>"+
            " </where> " +
            "ORDER BY kcu_category.sort,kcu_category.id" +
            "</script>")
    List<KcuCategory> listKcuCategoryByConditionsFirst(@Param("pid") Integer pid,@Param("type") Integer type,@Param("kcusystem_code") String kcusystem_code,@Param("title") String title);


    /**
     * @Description: 根据pid查询二级分类
     * @Param: [pid, system_code]
     * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuCategory>
     * @Author: LuoYu
     * @Date: 2018/7/23
     */
    @Select("<script> "+"SELECT kcu_category.* FROM kcu_category " +
            "<where>"+
            "<if test=\"title!=null and title!=''\"> and kcu_category.title like '%${title}%' </if> "+
            "<if test=\"pid!=null\"> and kcu_category.pid = #{pid} </if>"+
            "<if test=\"type!=null\"> and kcu_category.type = #{type} </if>"+
            " </where> " +
            " ORDER BY kcu_category.sort,kcu_category.id" +
            "</script>")
    List<KcuCategory> listKcuCategoryByConditionsSeconde(@Param("pid") Integer pid,@Param("type") Integer type,@Param("title") String title);

   /* *//**
    * @Description: 根据系统code和title和pid查询分类详情
    * @Param: [pid, system_code, title]
    * @return: com.manji.ackservice.model.kcumodel.KcuCategory
    * @Author: LuoYu
    * @Date: 2018/8/7
    */
    @Select("SELECT kcu_category.* FROM kcu_category INNER JOIN kcu_system_category " +
            "ON kcu_category.id = kcu_system_category.kcucategory_id" +
            " WHERE kcu_system_category.kcusystem_code = #{system_code} " +
            "AND kcu_category.pid = #{pid} and kcu_category.title = #{title} ")
    KcuCategory getKcuCategoryByIdAndSystemCode(@Param("pid") Integer pid,@Param("system_code") String system_code,@Param("title") String title);
}
