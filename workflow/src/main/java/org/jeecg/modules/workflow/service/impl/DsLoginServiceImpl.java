package org.jeecg.modules.workflow.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.workflow.service.DsBase;
import org.jeecg.modules.workflow.service.DsLoginService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DsLoginServiceImpl extends DsBase implements DsLoginService {

    @Override
    public String login() {
        String url = "login";
        Map<String,Object> params = new HashMap<>(16);
        params.put("userName",dolphinSchedulerUsename);
        params.put("userPassword",dolphinSchedulerPassword);
        HttpResponse response = HttpUtil.createPost(dolphinSchedulerUrl+url).header("token",dolphinSchedulerToken).form(params).execute();
        JSONObject result = JSONObject.parseObject(response.body());
        if(result.getIntValue("code")!=0){
            throw new RuntimeException(result.getString("msg"));
        }
        return JSONObject.parseObject(result.getString("data")).getString("sessionId");
    }
}
