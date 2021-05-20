package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;

public interface WaterfallDataSourceTypeMapper extends BaseMapper<WaterfallDataSourceType> {

  int deleteByPrimaryKey(Integer id);

  int insert(WaterfallDataSourceType record);

  int insertSelective(WaterfallDataSourceType record);

  WaterfallDataSourceType selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(WaterfallDataSourceType record);

  int updateByPrimaryKey(WaterfallDataSourceType record);
}