package com.manji.ackservice.Service;

import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.Category;

import java.util.List;

/**
 * Created by baiqiang on 2018-6-21.
 */
public interface ICategoryService {

    ResultData getCategoryList(Integer pid,String system_code);

    ResultData getAllCategory(Integer pid,String system_code);

    ResultData add(Category category);

    ResultData update(Category category);

    ResultData delete(Integer id);
}
