package org.jeecg.executor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.executor.dto.JobDTO;
import org.jeecg.executor.dto.WaterfallModel;

public interface WaterfallModelMapper  extends BaseMapper<WaterfallModel> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallModel record);

    int insertSelective(WaterfallModel record);

    WaterfallModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallModel record);

    int updateByPrimaryKey(WaterfallModel record);

    JobDTO getDbInfo(@Param(value = "jobId") Integer jobId);
}
