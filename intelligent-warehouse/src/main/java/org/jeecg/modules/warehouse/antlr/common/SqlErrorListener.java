package org.jeecg.modules.warehouse.antlr.common;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.jeecg.common.exception.JeecgBootException;

/**
 * @Author liansongye
 * @create 2021/8/31 1:42 下午
 */
@Slf4j
public class SqlErrorListener extends BaseErrorListener {


    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int charPositionInLine,
                            String msg,
                            RecognitionException e) {
        log.info("sql解析错误", msg);
        throw new JeecgBootException(msg);
    }
}
