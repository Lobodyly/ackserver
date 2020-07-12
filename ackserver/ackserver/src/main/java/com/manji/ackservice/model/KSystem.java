package com.manji.ackservice.model;

import com.manji.ackservice.MybatisUtil.Invisible;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by baiqiang on 2018-6-21.
 */
@Data
public class KSystem {
    @Invisible
    private Integer id;
    private String sys_code;
    private String sys_name;
    private String web_url;
    private String add_time;
    private Boolean is_delete;
    private String remark;

}
