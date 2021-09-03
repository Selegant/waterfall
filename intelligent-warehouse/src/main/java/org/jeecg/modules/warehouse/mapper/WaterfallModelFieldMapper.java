package org.jeecg.modules.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.warehouse.model.WaterfallModelField;

public interface WaterfallModelFieldMapper extends BaseMapper<WaterfallModelField> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallModelField record);

    int insertSelective(WaterfallModelField record);

    WaterfallModelField selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallModelField record);

    int updateByPrimaryKey(WaterfallModelField record);

    void deleteByModelId(Integer modelId);
}
