package org.jeecg.modules.datasources.webscoket;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Detective
 * @date Created in 2021/8/31
 */
public interface WebsocketCommandService {

    String perform(String json);

}
