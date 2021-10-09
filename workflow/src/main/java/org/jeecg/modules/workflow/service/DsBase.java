package org.jeecg.modules.workflow.service;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author wangtao
 * @date 2021-09-28 09:19
 */
public class DsBase {

    @Value("${dolphin.scheduler.url}")
    public String dolphinSchedulerUrl;

    @Value("${dolphin.scheduler.token}")
    public String dolphinSchedulerToken;

    @Value("${dolphin.scheduler.username}")
    public String dolphinSchedulerUsename;

    @Value("${dolphin.scheduler.password}")
    public String dolphinSchedulerPassword;
}
