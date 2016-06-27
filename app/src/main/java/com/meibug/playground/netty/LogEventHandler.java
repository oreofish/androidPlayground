package com.meibug.playground.netty;

import android.util.Log;

import com.meibug.playground.rx.RxBus;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by xing on 16/6/23.
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> { //1

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace(); //2
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, LogEvent event) throws Exception {
        RxBus.INSTANCE.send(event);
        Log.d("LogEventHandler", event.getMsg().get(0));
    }
}