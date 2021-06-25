package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallOfflineTask;

public interface WaterfallOfflineTaskMapper extends BaseMapper<WaterfallOfflineTask> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallOfflineTask record);

    int insertSelective(WaterfallOfflineTask record);

    WaterfallOfflineTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallOfflineTask record);

    int updateByPrimaryKey(WaterfallOfflineTask record);
}
