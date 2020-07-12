package com.manji.ackservice.mapper.kcumapper;

import com.manji.ackservice.MybatisUtil.SimpleInsertLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleSelectInLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleSelectLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleUpdateLangDriver;
import com.manji.ackservice.model.kcumodel.KcuInformation;
import org.apache.ibatis.annotations.*;

import javax.crypto.KeyGenerator;
import java.util.Date;
import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/23
 * Time:11:04
 */
@Mapper
public interface KcuInformationMapper {

    
    /** 
    * @Description: 新增知识点
    * @Param: [kcuInformation] 
    * @return: boolean 
    * @Author: LuoYu 
    * @Date: 2018/7/24 
    */ 
    @Insert("insert into kcu_information (#{kcuInformation})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    boolean addKcuInformation(KcuInformation kcuInformation);

    /**
    * @Description: 修改知识点
    * @Param: [kcuInformation]
    * @return: boolean
    * @Author: LuoYu
    * @Date: 2018/7/24
    */
    @Update("update kcu_information (#{kcuInformation}) where id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateKcuInformation(KcuInformation kcuInformation);
    
    /** 
    * @Description: 根据id修改启动状态,1为启动,0为禁止
    * @Param: [id, state, update_user_name, update_time] 
    * @return: boolean 
    * @Author: LuoYu 
    * @Date: 2018/7/24 
    */ 
    @Update("update kcu_information set state = #{state} , update_user_name = #{update_user_name} , update_time = #{update_time} where id = #{id}")
    boolean updateState(@Param("id") Integer id, @Param("state") Integer state, @Param("update_user_name") String update_user_name, @Param("update_time") Date update_time);

    /**
    * @Description: 根据id修改热门状态，1为热门，0为非热门
    * @Param: [id, type, update_user_name, update_time]
    * @return: boolean
    * @Author: LuoYu
    * @Date: 2018/7/24
    */
    @Update("update kcu_information set type = #{type} , update_user_name = #{update_user_name} , update_time = #{update_time} where id = #{id}")
    boolean updateType (@Param("id") Integer id,@Param("type") Integer type, @Param("update_user_name") String update_user_name, @Param("update_time") Date update_time);

    /**
    * @Description: 根据id使浏览量加1
    * @Param: [id]
    * @return: boolean
    * @Author: LuoYu
    * @Date: 2018/7/24
    */
    @Update("update kcu_information set view_number=view_number+1 where id = #{id}")
    boolean updateViewNumber(@Param("id") Integer id);

    /**
    * @Description: 根据id删除知识点
    * @Param: [id]
    * @return: boolean
    * @Author: LuoYu
    * @Date: 2018/7/24
    */
    @Delete("delete from kcu_information where id in (${id})")
    @Lang(SimpleSelectInLangDriver.class)
    boolean deleteKcuInformation(@Param("id") Integer id);

    /** 
    * @Description: 条件查询相应的知识点 
    * @Param: [kcuInformation] 
    * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuInformation> 
    * @Author: LuoYu 
    * @Date: 2018/7/24 
    */ 
    @Select("select id,title,add_time,update_time,add_user_name,update_user_name,view_number,remark,state,type,kcucategory_id from kcu_information  (#{kcuInformation}) order by view_number desc,id")
    @Lang(SimpleSelectLangDriver.class)
    List<KcuInformation> listKcuInformationAll(KcuInformation kcuInformation);


    /**
    * @Description:  根据id查询单条知识点
    * @Param: [id]
    * @return: com.manji.ackservice.model.kcumodel.KcuInformation
    * @Author: LuoYu
    * @Date: 2018/7/24
    */
    @Select("select * from kcu_information where id = #{id}")
    KcuInformation getKcuInformationById(@Param("id") Integer id);


/*
    @Select("<script> "+"SELECT * FROM kcu_information cf " +
            "INNER JOIN kcu_category cc ON cc.id = cf.kcucategory_id " +
            "INNER JOIN kcu_system_category sc ON sc.kcucategory_id = cc.id " +
            "AND sc.kcusystem_code = #{kcusystem_code} "+
            "INNER JOIN kcu_category cc1 ON cc1.id = cc.pid and cc1.id = #{id} " +
            "INNER JOIN kcu_system_category sc1 ON sc1.kcucategory_id = cc1.id " +
            "AND sc1.kcusystem_code = #{kcusystem_code} " +
            "<where>"+
            "<if test=\"title!=null and title!=''\"> and cf.title like '%${title}%' </if> "+
            "<if test=\"state!=null\"> and cf.state = #{state} </if>"+
            "<if test=\"type!=null\"> and cf.type = #{type} </if>"+
            " </where> " +
            "ORDER BY cf.id desc "+
            "</script>")
*/
    /**
     * @Description: 根据一级目录查询知识点列表
     * @Param:
     * @return:
     * @Author: LuoYu
     * @Date: 2018/8/3
     */
    @Select("<script> "+"SELECT cf.id,cf.title,cf.add_time,cf.update_time,cf.add_user_name,cf.update_user_name,cf.view_number,cf.remark,cf.state,cf.type,cf.kcucategory_id FROM kcu_information cf " +
            "INNER JOIN kcu_category cc ON cc.id = cf.kcucategory_id " +
            "INNER JOIN kcu_category cc1 ON cc1.id = cc.pid and cc1.id = #{id} " +
            "INNER JOIN kcu_system_category sc ON sc.kcucategory_id = cc1.id " +
            "AND sc.kcusystem_code = #{kcusystem_code} " +
            "<where>"+
            "<if test=\"title!=null and title!=''\"> and cf.title like '%${title}%' </if> "+
            "<if test=\"state!=null\"> and cf.state = #{state} </if>"+
            "<if test=\"type!=null\"> and cf.type = #{type} </if>"+
            " </where> " +
            "ORDER BY cf.id desc "+
            "</script>")
    List<KcuInformation> listKcuInformationByFirst(@Param("id") Integer id,@Param("state") Integer state,@Param("type") Integer type,@Param("kcusystem_code") String kcusystem_code,@Param("title") String title);


    /**
     * @Description: 根据二级目录查询知识点列表
     * @Param: [id, kcusystem_code, title]
     * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuInformation>
     * @Author: LuoYu
     * @Date: 2018/8/3
     */
    @Select("<script> "+"SELECT cf.id,cf.title,cf.add_time,cf.update_time,cf.add_user_name,cf.update_user_name,cf.view_number,cf.remark,cf.state,cf.type,cf.kcucategory_id FROM kcu_information cf " +
            "<where>"+
            "<if test=\"id!=null\"> and cf.kcucategory_id = #{id} </if>"+
            "<if test=\"title!=null and title!=''\"> and cf.title like '%${title}%' </if> "+
            "<if test=\"state!=null\"> and cf.state = #{state} </if>"+
            "<if test=\"type!=null\"> and cf.type = #{type} </if>"+
            " </where> " +
            "ORDER BY cf.id desc" +
            "</script>")
    List<KcuInformation> listKcuInformationBySecond(@Param("id") Integer id,@Param("state") Integer state,@Param("type") Integer type,@Param("title") String title);


    /** 
    * @Description: 根据系统code查询所有知识点列表 
    * @Param: [id, kcusystem_code, title] 
    * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuInformation> 
    * @Author: LuoYu 
    * @Date: 2018/8/3 
    */ 
    @Select("<script> "+"SELECT cf.id,cf.title,cf.add_time,cf.update_time,cf.add_user_name,cf.update_user_name,cf.view_number,cf.remark,cf.state,cf.type,cf.kcucategory_id FROM kcu_information cf " +
            "INNER JOIN kcu_category cc ON cc.id = cf.kcucategory_id " +
            "INNER JOIN kcu_category cc1 ON cc1.id = cc.pid " +
            "INNER JOIN kcu_system_category sc ON sc.kcucategory_id = cc1.id " +
            "AND sc.kcusystem_code = #{kcusystem_code} " +
            "<where>"+
            "<if test=\"title!=null and title!=''\"> and cf.title like '%${title}%' </if> "+
            "<if test=\"state!=null\"> and cf.state = #{state} </if>"+
            "<if test=\"type!=null\"> and cf.type = #{type} </if>"+
            " </where> " +
            "ORDER BY cf.id desc " +
            "</script>")
    List<KcuInformation> listKcuInformation(@Param("id") Integer id,@Param("state") Integer state,@Param("type") Integer type,@Param("kcusystem_code") String kcusystem_code,@Param("title") String title);

}



