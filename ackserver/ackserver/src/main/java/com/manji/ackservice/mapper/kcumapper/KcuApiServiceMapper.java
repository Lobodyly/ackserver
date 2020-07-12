package com.manji.ackservice.mapper.kcumapper;

import com.manji.ackservice.model.kcumodel.KcuInformation;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/8/4
 * Time:10:07
 */
@Mapper
public interface KcuApiServiceMapper {

    /**
     * @Description: 根据一级目录查询知识点列表
     * @Param:
     * @return:
     * @Author: LuoYu
     * @Date: 2018/8/3
     */
    @Select("SELECT cf.id,cf.title FROM kcu_information cf " +
            "INNER JOIN kcu_category cc ON cc.id = cf.kcucategory_id " +
            "INNER JOIN kcu_category cc1 ON cc1.id = cc.pid and cc1.id = #{id} " +
            "INNER JOIN kcu_system_category sc ON sc.kcucategory_id = cc1.id " +
            "AND sc.kcusystem_code = #{kcusystem_code} " +
            "ORDER BY cf.type  desc,cf.view_number desc " )
    List<Map> listKcuInformationByFirst(@Param("id") Integer id, @Param("kcusystem_code") String kcusystem_code);


    /**
     * @Description: 根据二级目录查询知识点列表
     * @Param: [id, kcusystem_code, title]
     * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuInformation>
     * @Author: LuoYu
     * @Date: 2018/8/3
     */
    @Select("SELECT id,title FROM kcu_information " +
            "Where kcucategory_id = #{id} " +
            "ORDER BY type  desc,view_number desc ")
    List<Map> listKcuInformationBySecond(@Param("id") Integer id);


    /**
     * @Description: 根据系统code查询所有知识点列表
     * @Param: [id, kcusystem_code, title]
     * @return: java.util.List<com.manji.ackservice.model.kcumodel.KcuInformation>
     * @Author: LuoYu
     * @Date: 2018/8/3
     */
    @Select("SELECT cf.id,cf.title FROM kcu_information cf " +
            "INNER JOIN kcu_category cc ON cc.id = cf.kcucategory_id " +
            "INNER JOIN kcu_category cc1 ON cc1.id = cc.pid " +
            "INNER JOIN kcu_system_category sc ON sc.kcucategory_id = cc1.id " +
            "AND sc.kcusystem_code = #{kcusystem_code} " +
            "ORDER BY cf.type  desc,cf.view_number desc")
    List<Map> listKcuInformation(@Param("id") Integer id,@Param("kcusystem_code") String kcusystem_code);
}
