package org.jeecg.modules.datasources.webscoket;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.datasources.input.WebsocketLogPageInput;
import org.jeecg.modules.datasources.model.WaterfallJobLog;
import org.jeecg.modules.datasources.service.IWaterfallJobLogService;

/**
 * @author Detective
 * @date Created in 2021/8/31
 */
public class JobLogCommandImpl implements WebsocketCommandService {

    @Override
    public String perform(String json) {
        WebsocketLogPageInput input = JSONObject.parseObject(json, WebsocketLogPageInput.class);
        IPage<WaterfallJobLog> page = SpringUtil.getBean(IWaterfallJobLogService.class).pages(input);
        if (page != null) {
            return JSONObject.toJSONString(page);
        }
        return "";
    }
}
