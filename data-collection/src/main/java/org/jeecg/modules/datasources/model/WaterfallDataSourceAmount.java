package org.jeecg.modules.datasources.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import lombok.Data;

@Data
public class WaterfallDataSourceAmount {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据源ID
     */
    private Integer dbId;

    /**
     * 数据量
     */
    private Integer amount;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 同步所需时间 单位(秒)
     */
    private Integer requiredTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 表名
     */
    private String tableName;
}