package org.jeecg.modules.datasources.core.route.strategy;



import org.jeecg.datax.biz.model.ReturnT;
import org.jeecg.datax.biz.model.TriggerParam;
import org.jeecg.modules.datasources.core.route.ExecutorRouter;

import java.util.List;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteFirst extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList){
        return new ReturnT<String>(addressList.get(0));
    }

}
