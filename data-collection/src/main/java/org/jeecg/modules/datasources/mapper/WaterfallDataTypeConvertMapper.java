package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallDataTypeConvert;

public interface WaterfallDataTypeConvertMapper  extends BaseMapper<WaterfallDataTypeConvert> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallDataTypeConvert record);

    int insertSelective(WaterfallDataTypeConvert record);

    WaterfallDataTypeConvert selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallDataTypeConvert record);

    int updateByPrimaryKey(WaterfallDataTypeConvert record);
}
