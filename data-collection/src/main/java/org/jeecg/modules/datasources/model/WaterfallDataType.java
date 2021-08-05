package org.jeecg.modules.datasources.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class WaterfallDataType {
    /**
    * 主键ID
    */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
    * 数据源类型
    */
    private String dbType;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 修改时间
    */
    private Date updateTime;

    /**
    * 数据类型名称
    */
    private String dataTypeName;
}
