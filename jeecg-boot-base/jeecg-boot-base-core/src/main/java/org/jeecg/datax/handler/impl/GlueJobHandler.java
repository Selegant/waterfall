package org.jeecg.datax.handler.impl;

import org.jeecg.datax.biz.model.ReturnT;
import org.jeecg.datax.biz.model.TriggerParam;
import org.jeecg.datax.handler.IJobHandler;
import org.jeecg.datax.log.JobLogger;

/**
 * glue job handler
 * @author xuxueli 2016-5-19 21:05:45
 */
public class GlueJobHandler extends IJobHandler {

	private long glueUpdatetime;
	private IJobHandler jobHandler;
	public GlueJobHandler(IJobHandler jobHandler, long glueUpdatetime) {
		this.jobHandler = jobHandler;
		this.glueUpdatetime = glueUpdatetime;
	}
	public long getGlueUpdatetime() {
		return glueUpdatetime;
	}

	@Override
	public ReturnT<String> execute(TriggerParam tgParam) throws Exception {
		JobLogger.log("----------- glue.version:"+ glueUpdatetime +" -----------");
		return jobHandler.execute(tgParam);
	}
}
