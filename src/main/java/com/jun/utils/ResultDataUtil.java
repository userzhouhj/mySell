package com.jun.utils;

import com.jun.vo.ResultData;

public class ResultDataUtil {

    public static ResultData success(Object o){
        ResultData resultData = new ResultData();
        resultData.setCode(0);
        resultData.setMsg("成功");
        if(o != null){
            resultData.setData(o);
        }

        return resultData;
    }

    public static ResultData fail(){
        ResultData resultData = new ResultData();
        resultData.setMsg("失败");
        resultData.setCode(1);
        resultData.setData(null);
        return resultData;
    }

}
