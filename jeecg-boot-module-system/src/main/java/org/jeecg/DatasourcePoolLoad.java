package org.jeecg;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.datasources.util.DatasourcePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author selegant
 */
@Component
@Slf4j
public class DatasourcePoolLoad implements ApplicationRunner {

    @Autowired
    DatasourcePool datasourcePool;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("启动完成后加载数据源连接池");
        log.info("开始加载");
        datasourcePool.initPoolMap();
        log.info("加载完成");
    }

}
