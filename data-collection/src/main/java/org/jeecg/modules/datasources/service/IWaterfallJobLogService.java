package org.jeecg.modules.datasources.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.datasources.input.WebsocketLogPageInput;
import org.jeecg.modules.datasources.model.WaterfallJobLog;

/**
 * @author Detective
 * @date Created in 2021/8/26
 */
public interface IWaterfallJobLogService extends IService<WaterfallJobLog> {

    IPage<WaterfallJobLog> pages(WebsocketLogPageInput input);
}
