package com.manji.ackservice.model.kcumodel;

import lombok.Data;

import java.util.Date;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/25
 * Time:9:45
 */
@Data
public class KcuFeedback {
    private Integer id;
    private Integer user_id;
    private String user_name;
    private String user_tel;
    private Integer kcuinformation_id;
    private String feedback_content;
    private Date add_time;
    private Integer state;
    private String system_code;
    private String kcuinformation_title;
}
