package org.jeecg.modules.datasources.core.route.strategy;



import org.jeecg.xxl.biz.model.ReturnT;
import org.jeecg.xxl.biz.model.TriggerParam;
import org.jeecg.modules.datasources.core.route.ExecutorRouter;

import java.util.List;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteLast extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return new ReturnT<String>(addressList.get(addressList.size()-1));
    }

}
