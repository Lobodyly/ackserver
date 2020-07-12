package com.manji.ackservice.Service;

import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.KSystem;

public interface IKSystemService {

    ResultData getSystemListPage(Integer pageNum, Integer pageSize);

    ResultData add(KSystem kSystem);

    ResultData update(KSystem kSystem);

    ResultData delete(Integer id);
}