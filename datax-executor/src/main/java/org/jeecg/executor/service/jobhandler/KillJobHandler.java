package org.jeecg.executor.service.jobhandler;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import org.jeecg.datax.biz.model.ReturnT;
import org.jeecg.datax.biz.model.TriggerParam;
import org.jeecg.datax.handler.IJobHandler;
import org.jeecg.datax.handler.annotation.JobHandler;
import org.jeecg.datax.util.ProcessUtil;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * DataX任务终止
 *
 * @author jingwk 2019-12-16
 */

@JobHandler(value = "killJobHandler")
@Component
public class KillJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(TriggerParam tgParam) {
        String processId = tgParam.getProcessId();
        boolean result = ProcessUtil.killProcessByPid(processId);
        //  删除临时文件
        if (!CollUtil.isEmpty(jobTmpFiles)) {
            String pathname = jobTmpFiles.get(processId);
            if (pathname != null) {
                FileUtil.del(new File(pathname));
                jobTmpFiles.remove(processId);
            }
        }
        return result ? IJobHandler.SUCCESS : IJobHandler.FAIL;
    }
}
