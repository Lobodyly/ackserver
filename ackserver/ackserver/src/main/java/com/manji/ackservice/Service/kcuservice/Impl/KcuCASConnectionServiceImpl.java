package com.manji.ackservice.Service.kcuservice.Impl;

import com.manji.ackservice.Service.kcuservice.IKcuCASConnectionService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.mapper.kcumapper.KcuConnectionMapper;
import com.manji.ackservice.model.kcumodel.KcuCASConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/24
 * Time:15:07
 */
@Service
public class KcuCASConnectionServiceImpl implements IKcuCASConnectionService {
    @Autowired
    private KcuConnectionMapper kcuConnectionMapper;

    /**
    * @Description: 添加系统与分类关联信息
    * @Param: [kcuCategoryId, kcuSystemCode]
    * @return: com.manji.ackservice.common.model.ResultData
    * @Author: LuoYu
    * @Date: 2018/7/25
    */
    @Override
    public ResultData addSysAndCateConnetion (Integer kcuCategoryId,String kcuSystemCode) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"系统关联分类成功");
        boolean is_success = kcuConnectionMapper.addSysAndCateConnetion(kcuCategoryId,kcuSystemCode);
        if (!is_success) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("系统关联分类失败");
            return resultData;
        }
        return resultData;
    }

    /**
    * @Description: 根据系统code删除关联表
    * @Param: [kcuSystemCode]
    * @return: com.manji.ackservice.common.model.ResultData
    * @Author: LuoYu
    * @Date: 2018/7/25
    */
    @Override
    public ResultData deleteByKcuSystemCode (String kcuSystemCode) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"删除系统与分类的关联信息成功");
        boolean is_success = kcuConnectionMapper.deleteByKcuSystemCode(kcuSystemCode);
        if (!is_success) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("删除系统与分类的关联信息失败");
            return resultData;
        }
        return resultData;
    }

    /**
     * @Description: 根据分类id删除关联表
     * @Param: [kcucategoryId]
     * @return: com.manji.ackservice.common.model.ResultData
     * @Author: LuoYu
     * @Date: 2018/7/25
     */
    @Override
    public ResultData deleteByKcuCategoryId(Integer kcucategoryId) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"删除系统与分类的关联信息成功");
        boolean is_success = kcuConnectionMapper.deleteByKcucategoryId(kcucategoryId);
        if (!is_success) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("删除系统与分类的关联信息失败");
            return resultData;
        }
        return resultData;
    }

    @Override
    public ResultData listCAIConnection (KcuCASConnection kcuCASConnection) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"获取系统与分类的关联信息成功");
        List<KcuCASConnection> kcuCASConnectionList = kcuConnectionMapper.listCAIConnection(kcuCASConnection);
        if (null == kcuCASConnectionList) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("获取系统与分类的关联信息失败");
            return resultData;
        }
        resultData.push("list", kcuCASConnectionList);
        return resultData;
    }
}
