package com.manji.ackservice.model.kcumodel;

import lombok.Data;

import java.util.Date;

@Data
public class KcuCategory {
    private Integer id;
    private Integer pid;
    private String title;
    private Date add_time;
    private String remark;
    private Integer sort;
    private Integer type;
    private Integer leaf;
}
