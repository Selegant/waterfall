package org.jeecg.executor.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data

public class WaterfallModel {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer folderId;

    private String modelName;

    private Integer modelStatusCode;

    private String modelStatusName;

    private Integer exportTypeCode;

    private String exportTypeName;

    private String remark;

    private Integer publishDb;

    private Date createTime;

    private Date updateTime;

    private Boolean delFlag;
}
