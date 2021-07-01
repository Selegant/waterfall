package org.jeecg.modules.datasources.dto;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

@Data
public class JobResultDTO {

    private SettingBean setting;

    private List<ContentBean> content;

    @Data
    public static class SettingBean {

        private ErrorLimitBean errorLimit;

        private SpeedBean speed;

        @Data
        public static class ErrorLimitBean {

            private Double percentage;

            private Integer record;

        }

        @Data
        public static class SpeedBean {

            private Integer channel;

            @JSONField(name = "byte")
            private Integer bytes;
        }
    }

    @Data
    public static class ContentBean {

        private ReaderBean reader;

        private WriterBean writer;

        @Data
        public static class ReaderBean {

            private String name;

            private ParameterBean parameter;

            @Data
            public static class ParameterBean {

                private String password;

                private String username;

                private List<String> column;

                private List<ConnectionBean> connection;

            }
        }

        @Data
        public static class WriterBean {

            private String name;

            private ParameterBeanX parameter;

            @Data
            public static class ParameterBeanX {

                private String encoding;

                private Boolean print;

                private String password;

                private String username;

                private List<String> column;

                private List<ConnectionBeanW> connection;

                @Data
                public static class ConnectionBeanW {

                    private String jdbcUrl;

                    private List<String> table;
                }
            }
        }
    }

    @Data
    public static class ConnectionBean {

        private List<String> jdbcUrl;

        private List<String> table;
    }
}
