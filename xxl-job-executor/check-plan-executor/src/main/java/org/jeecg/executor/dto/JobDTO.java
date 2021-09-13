package org.jeecg.executor.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author liansongye
 * @create 2021/9/6 9:43 上午
 */
@Data
public class JobDTO {
    private String driver;
    private String jdbcUrl;
    private String dbName;
    private String username;
    private String password;
    private String folderId;
    private String tableName;

    private List<String> emptyCheck = new ArrayList<>();
    private Map<String, String> cornCheck = new HashMap<>();
    private Map<String, String> compareCheck = new HashMap<>();
}
