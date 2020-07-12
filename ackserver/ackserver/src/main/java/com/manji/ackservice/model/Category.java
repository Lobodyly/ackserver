package com.manji.ackservice.model;

import com.manji.ackservice.MybatisUtil.Invisible;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 知识库分类
 * Created by baiqiang on 2018-6-21.
 */
@Data
public class Category {
    @Invisible
    private Integer id;
    private String system_code;
    private Integer pid;
    private String title;
    private String add_time;
    private String remark;
}
