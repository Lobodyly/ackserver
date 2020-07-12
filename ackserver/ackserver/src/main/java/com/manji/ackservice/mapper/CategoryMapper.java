package com.manji.ackservice.mapper;

import com.manji.ackservice.MybatisUtil.SimpleInsertLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleUpdateLangDriver;
import com.manji.ackservice.model.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by baiqiang on 2018-6-21.
 */

public interface CategoryMapper {

    @Select("SELECT * FROM k_category WHERE pid=#{pid} and system_code=#{system_code}")
    List<Category> getCategoryListByPidAndSyscode(@Param("pid")Integer pid,@Param("system_code")String system_code);

    @Select("SELECT * FROM k_category WHERE pid=#{pid}")
    List<Category> getCategoryListByPid(@Param("pid")Integer pid);

    @Select("SELECT * FROM k_category")
    List<Category> getCategoryALLList();

    @Select("SELECT * FROM k_category WHERE id=#{id}")
    Category getCategoryByid(@Param("id")Integer id);

    @Select("SELECT * FROM k_category WHERE pid=#{pid} and title=#{title}")
    Category getCategoryListByPidAndTitle(@Param("pid")Integer pid,@Param("title")String title);

    @Insert("INSERT INTO k_category (#{category})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Lang(SimpleInsertLangDriver.class)
    boolean  add(Category category);

    @Update("UPDATE k_category (#{category}) WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean update(Category category);

    @Delete("delete from k_category WHERE id = #{id}")
    boolean delete(@Param("id") Integer id);


}
