package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallDictModelType;

public interface WaterfallDictModelTypeMapper extends BaseMapper<WaterfallDictModelType> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallDictModelType record);

    int insertSelective(WaterfallDictModelType record);

    WaterfallDictModelType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallDictModelType record);

    int updateByPrimaryKey(WaterfallDictModelType record);
}
