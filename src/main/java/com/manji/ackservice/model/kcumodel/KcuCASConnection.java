package com.manji.ackservice.model.kcumodel;

import lombok.Data;

/**
 * 知识点id和分类id的连接
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/24
 * Time:14:22
 */
@Data
public class KcuCASConnection {

    private Integer kcucategory_id;
    private String kcusystem_code;
}
