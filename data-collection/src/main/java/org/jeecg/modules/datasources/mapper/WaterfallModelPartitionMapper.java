package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallModelPartition;

public interface WaterfallModelPartitionMapper  extends BaseMapper<WaterfallModelPartition> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallModelPartition record);

    int insertSelective(WaterfallModelPartition record);

    WaterfallModelPartition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallModelPartition record);

    int updateByPrimaryKey(WaterfallModelPartition record);
}
