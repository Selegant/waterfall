package org.jeecg.modules.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.warehouse.dto.WaterfallQualityCheckPlanDTO;
import org.jeecg.modules.warehouse.model.WaterfallQualityModelWithJobInfo;
import org.mapstruct.Mapper;

@Mapper
public interface WaterfallQualityModelWithJobInfoMapper extends BaseMapper<WaterfallQualityModelWithJobInfo> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallQualityModelWithJobInfo record);

    int insertSelective(WaterfallQualityModelWithJobInfo record);

    WaterfallQualityModelWithJobInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallQualityModelWithJobInfo record);

    int updateByPrimaryKey(WaterfallQualityModelWithJobInfo record);

    IPage<WaterfallQualityCheckPlanDTO> checkPlanList(Page<WaterfallQualityCheckPlanDTO> page, Integer modelId);

    WaterfallQualityCheckPlanDTO checkPlanInfo(Integer jobInfoId);
}
