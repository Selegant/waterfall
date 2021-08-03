package org.jeecg.modules.datasources.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class WaterfallDataType {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String dbType;

    private String dateTypeName;

    private Date createTime;

    private Date updateTime;
}
