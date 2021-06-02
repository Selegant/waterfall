package org.jeecg.modules.datasources.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author selegant
 */
@Data
public class DatabaseTreeDTO {

    private Object[] selectedKeys;
    private Object[] expandedKeys;
    private List<DatabaseTreeDTO.Tree> trees;

    @Data
    public static class Tree{
        public String title;

        public String key;

        public JSONObject slots;

        public List<DatabaseTreeDTO.Tree> children;
    }
}
