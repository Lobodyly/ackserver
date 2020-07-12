package com.manji.ackservice.model.kcumodel;

import lombok.Data;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/8/8
 * Time:1:27
 */
@Data
public class Feedback {
    private String sessionId;
    private Integer state;
    private String feedback_content;
    private String system_code;
    private Integer kcuinformation_id;
    private String kcuinformation_title;

}
