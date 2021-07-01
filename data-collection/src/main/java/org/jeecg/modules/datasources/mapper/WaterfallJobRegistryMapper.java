package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;import org.jeecg.modules.datasources.model.WaterfallJobRegistry;import java.util.Date;import java.util.List;

public interface WaterfallJobRegistryMapper extends BaseMapper<WaterfallJobRegistry> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallJobRegistry record);

    int insertSelective(WaterfallJobRegistry record);

    WaterfallJobRegistry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallJobRegistry record);

    int updateByPrimaryKey(WaterfallJobRegistry record);

    List<Integer> findDead(@Param("timeout") int timeout,
                           @Param("nowTime") Date nowTime);

    int removeDead(@Param("ids") List<Integer> ids);

    List<WaterfallJobRegistry> findAll(@Param("timeout") int timeout,
                                       @Param("nowTime") Date nowTime);

    int registryUpdate(@Param("registryGroup") String registryGroup,
                       @Param("registryKey") String registryKey,
                       @Param("registryValue") String registryValue,
                       @Param("cpuUsage") double cpuUsage,
                       @Param("memoryUsage") double memoryUsage,
                       @Param("loadAverage") double loadAverage,
                       @Param("updateTime") Date updateTime);

    int registrySave(@Param("registryGroup") String registryGroup,
                     @Param("registryKey") String registryKey,
                     @Param("registryValue") String registryValue,
                     @Param("cpuUsage") double cpuUsage,
                     @Param("memoryUsage") double memoryUsage,
                     @Param("loadAverage") double loadAverage,
                     @Param("updateTime") Date updateTime);

    int registryDelete(@Param("registryGroup") String registryGroup,
                       @Param("registryKey") String registryKey,
                       @Param("registryValue") String registryValue);
}
