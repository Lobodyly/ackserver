package com.manji.ackservice.mapper;

import com.manji.ackservice.MybatisUtil.SimpleInsertLangDriver;
import com.manji.ackservice.MybatisUtil.SimpleUpdateLangDriver;
import com.manji.ackservice.model.Information;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by baiqiang on 2018-6-21.
 */

public interface InformationMapper {

    @Select("<script> "+"SELECT * FROM k_information  " +
            "<where>"+
            "<if test=\"state!=null\"> and state = #{state} </if>"+
            "<if test=\"categoryid!=null and categoryid!=''\"> and category_id = #{categoryid} </if>"+
            "<if test=\"title!=null and title!=''\"> and title LIKE '%${title}%'  </if> "+
            " </where> " +
            "</script>")
    List<Information> getInformationList(@Param ("state") Integer state,@Param ("categoryid") Integer categoryid,@Param ("title") String title);

    @Select("SELECT * FROM k_information WHERE category_id=#{category_id} and title=#{title}")
    Information getInformationListByCidAndTitle(@Param("category_id")Integer category_id, @Param("title")String title);

    @Select("SELECT * FROM k_information WHERE id=#{id}")
    Information getInformationById(@Param("id")Integer id);

    @Insert("INSERT INTO k_information (#{information})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Lang(SimpleInsertLangDriver.class)
    boolean add(Information information);

    @Update("UPDATE k_information (#{information}) WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean update(Information information);


    @Delete("delete from k_information WHERE id in (${ids})")
    boolean delete(@Param("ids") String ids);

    @Update("UPDATE k_information set view_number=view_number+1 WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean view(@Param("id") Integer id);

    @Update("UPDATE k_information set state= #{state} WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean changeState (@Param("id") Integer id,@Param("state") Integer state);

}
