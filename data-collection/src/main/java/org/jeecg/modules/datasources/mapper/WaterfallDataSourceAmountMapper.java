package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.datasources.model.WaterfallDataSourceAmount;

public interface WaterfallDataSourceAmountMapper extends BaseMapper<WaterfallDataSourceAmount> {

    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallDataSourceAmount record);

    int insertSelective(WaterfallDataSourceAmount record);

    WaterfallDataSourceAmount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallDataSourceAmount record);

    int updateByPrimaryKey(WaterfallDataSourceAmount record);

    void insertBatch(@Param("addList") List<WaterfallDataSourceAmount> addList,@Param("type") Integer type);
}
