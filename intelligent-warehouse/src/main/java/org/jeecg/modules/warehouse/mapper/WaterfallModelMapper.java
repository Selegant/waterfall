package org.jeecg.modules.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.warehouse.dto.JobDTO;
import org.jeecg.modules.warehouse.model.WaterfallModel;

public interface WaterfallModelMapper  extends BaseMapper<WaterfallModel> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallModel record);

    int insertSelective(WaterfallModel record);

    WaterfallModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallModel record);

    int updateByPrimaryKey(WaterfallModel record);

    JobDTO getDbInfo(@Param(value = "jobId") Integer jobId);
}
