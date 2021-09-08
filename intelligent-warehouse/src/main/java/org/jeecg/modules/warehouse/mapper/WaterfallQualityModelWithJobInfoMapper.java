package org.jeecg.modules.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.warehouse.dto.WaterfallQualityCheckPlanDTO;
import org.jeecg.modules.warehouse.model.WaterfallQualityModelWithJobInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface WaterfallQualityModelWithJobInfoMapper extends BaseMapper<WaterfallQualityModelWithJobInfo> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallQualityModelWithJobInfo record);

    int insertSelective(WaterfallQualityModelWithJobInfo record);

    WaterfallQualityModelWithJobInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallQualityModelWithJobInfo record);

    int updateByPrimaryKey(WaterfallQualityModelWithJobInfo record);

    List<WaterfallQualityCheckPlanDTO> checkPlanList(Integer modelId);

    WaterfallQualityCheckPlanDTO checkPlanInfo(Integer jobInfoId);
}
