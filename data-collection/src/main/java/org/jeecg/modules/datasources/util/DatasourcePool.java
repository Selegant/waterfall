package org.jeecg.modules.datasources.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author shitiannan
 */
@Component
@Slf4j
public class DatasourcePool {

    public static Map<Integer, MyDatasourcePoolUtil> pool = new HashMap<>();

    @Autowired
    private WaterfallDataSourceMapper mapper;

//    @PostConstruct
    public void initPoolMap() {
        List<WaterfallDataSource> lstWaterfall = mapper.selectList(new QueryWrapper<>());
        for (WaterfallDataSource waterfall : lstWaterfall) {
            try {
                DataSource dataSource = MyDBUtil.createDruidPoolByHands(waterfall);
                MyDatasourcePoolUtil instance = new MyDatasourcePoolUtil();
                instance.init(dataSource);
                pool.put(waterfall.getId(), instance);
            } catch (Exception e) {
                log.error("线程池创建失败:{}", e.getMessage());
                System.exit(0);
            }
        }
    }
}
