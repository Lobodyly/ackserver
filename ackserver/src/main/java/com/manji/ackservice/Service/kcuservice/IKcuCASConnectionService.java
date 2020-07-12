package com.manji.ackservice.Service.kcuservice;

import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.kcumodel.KcuCASConnection;


/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/24
 * Time:14:59
 */
public interface IKcuCASConnectionService {

    ResultData addSysAndCateConnetion (Integer kcuCategoryId,String kcuSystemCode);

    ResultData deleteByKcuSystemCode (String kcuSystemCode);

    ResultData deleteByKcuCategoryId(Integer kcucategoryId);

    ResultData listCAIConnection (KcuCASConnection kcuCASConnection);
}
