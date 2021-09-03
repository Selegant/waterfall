package org.jeecg.modules.warehouse.dto;

import lombok.Data;

@Data
public class PageInfoDTO {

    private long totalPage;

    private long totalCount;

    private int pageNo;

    private int pageSize;

    private Object data;

}
