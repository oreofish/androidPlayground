package com.meibug.playground.netty;

import android.util.Log;

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
        Log.d("LogEventHandler", event.getMsg().get(0));
    }
}