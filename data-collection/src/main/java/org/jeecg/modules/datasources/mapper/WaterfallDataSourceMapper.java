package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSource;

public interface WaterfallDataSourceMapper  extends BaseMapper<WaterfallDataSource> {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(WaterfallDataSource record);

    WaterfallDataSource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallDataSource record);

    int updateByPrimaryKey(WaterfallDataSource record);
}
