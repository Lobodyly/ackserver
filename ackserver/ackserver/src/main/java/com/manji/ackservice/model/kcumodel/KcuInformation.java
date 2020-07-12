package com.manji.ackservice.model.kcumodel;

import lombok.Data;

import java.util.Date;

@Data
public class KcuInformation {
    private Integer id;
    private Integer kcucategory_id;
    private String title;
    private String content;
    private Date add_time;
    private Date update_time;
    private String add_user_name;
    private String update_user_name;
    private Integer view_number;
    private String remark;
    private Integer state;
    private Integer type;
}
