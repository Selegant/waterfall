package org.jeecg.modules.datasources.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceMapper;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceTypeMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;
import org.jeecg.modules.datasources.service.IDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author selegant
 */
public class DataSourceServiceImpl implements IDataSourceService {

    @Autowired
    WaterfallDataSourceMapper waterfallDataSourceMapper;

    @Autowired
    WaterfallDataSourceTypeMapper waterfallDataSourceTypeMapper;

    @Override
    public void saveDataSource(WaterfallDataSource dataSource) {
        waterfallDataSourceMapper.insertSelective(dataSource);
    }

    @Override
    public List<WaterfallDataSource> list() {
       return waterfallDataSourceMapper.selectList(new QueryWrapper<WaterfallDataSource>());
    }

    @Override
    public List<WaterfallDataSourceType> dataSourceTypeList() {
        return waterfallDataSourceTypeMapper.selectList(new QueryWrapper<WaterfallDataSourceType>());
    }
}
