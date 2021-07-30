package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;

public interface WaterfallDataSourceTypeMapper extends BaseMapper<WaterfallDataSourceType> {

  int deleteByPrimaryKey(Integer id);

  int insert(WaterfallDataSourceType record);

  int insertSelective(WaterfallDataSourceType record);

  WaterfallDataSourceType selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(WaterfallDataSourceType record);

  int updateByPrimaryKey(WaterfallDataSourceType record);

  String getDriverByDbType(@Param(value = "db_type")String dbType);
}
