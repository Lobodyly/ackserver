package com.manji.ackservice.Service;

import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.Information;

public interface IInformationService {

    ResultData getInformationListPage(Integer pageNum, Integer pageSize,Integer state,Integer categoryid,String title);

    ResultData getInformationById(Integer id);

    ResultData add(Information information);

    ResultData update(Information information);

    ResultData delete(String ids);

    ResultData view(Integer id);

    ResultData changeState(Integer id,Integer state);
}
