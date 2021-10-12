package org.jeecg.executor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.executor.dto.JobDTO;
import org.jeecg.executor.dto.WaterfallModel;

import java.util.List;

public interface HiveMetastoreMapper extends BaseMapper<WaterfallModel> {

    List<List<String>> getColumnInfo(@Param(value = "databaseName") String databaseName, @Param(value = "tableName") String tableName);
}
