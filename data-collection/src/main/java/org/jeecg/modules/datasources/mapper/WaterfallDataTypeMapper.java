package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallDataType;

public interface WaterfallDataTypeMapper extends BaseMapper<WaterfallDataType> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallDataType record);

    int insertSelective(WaterfallDataType record);

    WaterfallDataType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallDataType record);

    int updateByPrimaryKey(WaterfallDataType record);
}
