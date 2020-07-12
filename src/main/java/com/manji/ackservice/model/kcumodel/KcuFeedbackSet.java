package com.manji.ackservice.model.kcumodel;

import lombok.Data;


/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/25
 * Time:9:42
 */
@Data
public class KcuFeedbackSet {
    private Integer id;
    private String feedback_content;
    private Integer is_delete;
    private String system_code;
}
