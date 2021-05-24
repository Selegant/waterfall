package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.datasources.dto.WaterfallDataSourceListDTO;
import org.jeecg.modules.datasources.model.WaterfallDataSource;

public interface WaterfallDataSourceMapper extends BaseMapper<WaterfallDataSource> {

    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallDataSource record);

    int insertSelective(WaterfallDataSource record);

    WaterfallDataSource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallDataSource record);

    int updateByPrimaryKey(WaterfallDataSource record);

    List<WaterfallDataSourceListDTO> list(@Param("purpose") String purpose);
}