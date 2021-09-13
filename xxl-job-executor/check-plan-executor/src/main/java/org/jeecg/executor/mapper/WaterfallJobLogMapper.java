package org.jeecg.executor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.executor.dto.WaterfallJobLog;



public interface WaterfallJobLogMapper extends BaseMapper<WaterfallJobLog> {

    int deleteByPrimaryKey(Long id);

    int insert(WaterfallJobLog record);

    int insertSelective(WaterfallJobLog record);

    WaterfallJobLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WaterfallJobLog record);

    int updateByPrimaryKey(WaterfallJobLog record);
}
