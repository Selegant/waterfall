package org.jeecg.modules.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.warehouse.model.WaterfallFolder;

public interface WaterfallFolderMapper extends BaseMapper<WaterfallFolder> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallFolder record);

    int insertSelective(WaterfallFolder record);

    WaterfallFolder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallFolder record);

    int updateByPrimaryKey(WaterfallFolder record);
}
