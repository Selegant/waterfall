package org.jeecg.modules.datasources.webscoket;

import static org.jeecg.modules.datasources.webscoket.WebsocketCommandEnum.JOB_LOG_PAGES;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.WebsocketConst;
import org.jeecg.modules.datasources.input.WebsocketLogPageInput;
import org.jeecg.modules.datasources.model.WaterfallJobLog;
import org.jeecg.modules.datasources.service.IWaterfallJobLogService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Detective
 * @date Created in 2021/8/26
 */
@Component
@Slf4j
@ServerEndpoint("/logWebsocket/{userId}")
public class JobWebsocket {

    private Session session;

    private String userId;

    /**
     * 缓存 webSocket连接到单机服务class中（整体方案支持集群）
     */
    private static CopyOnWriteArraySet<JobWebsocket> webSockets = new CopyOnWriteArraySet<>();

    private static Map<String, Session> sessionPool = new HashMap<String, Session>();

    private static final String COMMAND = "command";

    private WebsocketCommandFactory factory = new WebsocketCommandFactory();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        try {
            this.session = session;
            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
            // 连接后先发送一下日志分页操作
//            String json = JSONObject.toJSONString(WebsocketLogPageInput.builder().pageNo(1).pageSize(10).build());
//            pushMessage(factory.factoryCommand(JOB_LOG_PAGES.getCommand()).perform(json), session);
            log.info("websocket消息 有新的连接，总数为:{}", webSockets.size());
        } catch (Exception e) {
            log.error("websocket连接异常:{}", e.getMessage());
        }
    }

    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息】连接断开，总数为:" + webSockets.size());
        } catch (Exception e) {
            log.error("websocket消息:{}", e.getMessage());
        }
    }

    /**
     * 服务器端推送消息
     */
    public void pushMessage(String message) {
        try {
            webSockets.forEach(ws -> ws.session.getAsyncRemote().sendText(message));
        } catch (Exception e) {
            log.error("websocket消息:{}", e.getMessage());
        }
    }

    public void pushMessage(String message, Session session) {
        session.getAsyncRemote().sendText(message);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息：{},{}", userId, message);
        JSONObject jsonObjParams = JSONObject.parseObject(message);
        Object objCommand = jsonObjParams.get(COMMAND);
        if (objCommand != null) {
            String returnMessage = factory.factoryCommand(objCommand.toString()).perform(message);
            session.getAsyncRemote().sendText(returnMessage);
        }
    }


}
