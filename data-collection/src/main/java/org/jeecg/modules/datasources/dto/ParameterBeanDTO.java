package org.jeecg.modules.datasources.dto;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @author Detective
 * @date Created in 2021/8/24
 */
@Data
public class ParameterBeanDTO {

    private String encoding;

    private Boolean print;

    private String password;

    private String username;

    private List<NameAndTypeDTO> column;

    private List<ConnectionBeanW> connection;

    private String defaultFS;

    private String path;

    private String fileName;

    private String fileType;

    private String writeMode;

    private JSONObject hadoopConfig;

    @Data
    public static class ConnectionBeanW {

        private String jdbcUrl;

        private List<String> table;
    }
}
