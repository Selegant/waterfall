package org.jeecg.modules.datasources.webscoket;

/**
 * @author Detective
 * @date Created in 2021/8/31
 */
public enum WebsocketCommandEnum {

    JOB_LOG_PAGES("/jobLogPages", "任务日志分页列表");

    private String command;

    private String name;

    WebsocketCommandEnum(String command, String name) {
        this.command = command;
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }
}
