package org.jeecg.modules.datasources.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WaterfallDataTypeConvert that = (WaterfallDataTypeConvert) o;
        return Objects.equals(source, that.source) &&
                Objects.equals(sourceDataType, that.sourceDataType) &&
                Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, sourceDataType, target);
    }
}
