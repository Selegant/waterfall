package org.jeecg.modules.datasources.util;

import java.util.regex.Pattern;

/**
 * 正则
 * @Author liansongye
 * @create 2021/8/30 11:03 上午
 */
public class RegularUtil {

    private static String SQL_COLUNM_NAME = "(^_([a-zA-Z0-9]_?)*$)|(^[a-zA-Z](_?[a-zA-Z0-9])*_?$)";

    public static boolean isSqlColunmName(String name) {
        return Pattern.matches(SQL_COLUNM_NAME, name);
    }
}
