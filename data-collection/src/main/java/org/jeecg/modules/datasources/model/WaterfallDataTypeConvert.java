package org.jeecg.modules.datasources.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class WaterfallDataTypeConvert {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String source;

    private String sourceDataType;

    private String target;

    private String targetDataType;

    private Date craeteTime;

    private Date updateTime;
}
