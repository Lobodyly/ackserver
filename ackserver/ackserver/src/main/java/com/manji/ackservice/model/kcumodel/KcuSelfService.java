package com.manji.ackservice.model.kcumodel;

import lombok.Data;

import java.util.Date;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/31
 * Time:10:09
 */
@Data
public class KcuSelfService {
    private Integer id;
    private String title;
    private String image_url;
    private String link_url;
    private Integer sort;
    private Date add_time;
    private Integer is_delete;
    private String system_code;
    private Integer type;
}
