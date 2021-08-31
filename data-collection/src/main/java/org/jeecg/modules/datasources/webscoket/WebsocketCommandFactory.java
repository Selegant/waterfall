package org.jeecg.modules.datasources.webscoket;

import static org.jeecg.modules.datasources.webscoket.WebsocketCommandEnum.JOB_LOG_PAGES;

/**
 * @author Detective
 * @date Created in 2021/8/31
 */
public class WebsocketCommandFactory {

    public WebsocketCommandService factoryCommand(String command) {
        if (JOB_LOG_PAGES.getCommand().equals(command)) {
            return new JobLogCommandImpl();
        }
        throw new RuntimeException("无效命令");
    }
}
