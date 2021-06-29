package org.jeecg.rpc.remoting.net.impl.netty.server;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.rpc.remoting.net.params.Beat;
import org.jeecg.rpc.remoting.net.params.XxlRpcRequest;
import org.jeecg.rpc.remoting.net.params.XxlRpcResponse;
import org.jeecg.rpc.remoting.provider.XxlRpcProviderFactory;
import org.jeecg.rpc.util.ThrowableUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * netty server handler
 *
 * @author xuxueli 2015-10-29 20:07:37
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<XxlRpcRequest> {

    private XxlRpcProviderFactory xxlRpcProviderFactory;
    private ThreadPoolExecutor serverHandlerPool;

    public NettyServerHandler(final XxlRpcProviderFactory xxlRpcProviderFactory, final ThreadPoolExecutor serverHandlerPool) {
        this.xxlRpcProviderFactory = xxlRpcProviderFactory;
        this.serverHandlerPool = serverHandlerPool;
    }


    @Override
    public void channelRead0(final ChannelHandlerContext ctx, final XxlRpcRequest xxlRpcRequest) throws Exception {

        // filter beat
        if (Beat.BEAT_ID.equalsIgnoreCase(xxlRpcRequest.getRequestId())){
            log.debug(">>>>>>>>>>> xxl-rpc provider netty server read beat-ping.");
            return;
        }

        // do invoke
        try {
            serverHandlerPool.execute(new Runnable() {
                @Override
                public void run() {
                    // invoke + response
                    XxlRpcResponse xxlRpcResponse = xxlRpcProviderFactory.invokeService(xxlRpcRequest);

                    ctx.writeAndFlush(xxlRpcResponse);
                }
            });
        } catch (Exception e) {
            // catch error
            XxlRpcResponse xxlRpcResponse = new XxlRpcResponse();
            xxlRpcResponse.setRequestId(xxlRpcRequest.getRequestId());
            xxlRpcResponse.setErrorMsg(ThrowableUtil.toString(e));

            ctx.writeAndFlush(xxlRpcResponse);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	log.error(">>>>>>>>>>> xxl-rpc provider netty server caught exception", cause);
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            ctx.channel().close();      // beat 3N, close if idle
            log.debug(">>>>>>>>>>> xxl-rpc provider netty server close an idle channel.");
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
