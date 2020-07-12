package com.manji.ackservice.model;

import com.manji.ackservice.MybatisUtil.Invisible;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by baiqiang on 2018-6-21.
 */
@Data
public class Information {
    @Invisible
    private Integer id;
    private Integer category_id;
    private String title;
    private String content;
    private String add_time;
    private String add_user_name;
    private String update_time;
    private String update_user_name;
    private Integer view_number;
    private String remark;
    private Integer state;
    private String system_code;
}
