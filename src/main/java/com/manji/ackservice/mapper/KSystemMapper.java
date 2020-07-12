package com.manji.ackservice.mapper;

import com.manji.ackservice.MybatisUtil.SimpleInsertLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleUpdateLangDriver;
import com.manji.ackservice.model.KSystem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by baiqiang on 2018-6-21.
 */

public interface KSystemMapper {

   /* @Select("<script>" +
            "SELECT * FROM k_system c where 1=1" +
            "<if test='title!=null and title != \"\" '>" +
            " and c.title like '%${title}%' " +
            "</if>" +
            "</script>")*/
    @Select("SELECT * FROM k_system where is_delete=0")
    List<KSystem> getKSystemList();

    @Insert("INSERT INTO k_system (#{kSystem})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Lang(SimpleInsertLangDriver.class)
    boolean  add(KSystem kSystem);

    @Update("UPDATE k_system (#{kSystem}) WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean update(KSystem kSystem);

    @Update("UPDATE  k_system set is_delete = 1 WHERE id = #{id}")
    boolean delete(@Param("id") Integer id);
}
