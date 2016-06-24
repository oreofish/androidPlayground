package com.meibug.playground.netty;

import android.util.Log;

import com.meibug.playground.events.ClientGetEvent;
import com.meibug.playground.rx.RxBus;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by xing on 16/6/23.
 */
@ChannelHandler.Sharable                                //1
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for(int i = 0; i < 50; i++) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("Netty " + i + " id:" + Thread.currentThread().getId(), CharsetUtil.UTF_8));
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        Log.w("EchoClientHandler " + Thread.currentThread().getId(), "Client received: " + in.toString(CharsetUtil.UTF_8));    //3
        RxBus.INSTANCE.send(new ClientGetEvent(in.toString(CharsetUtil.UTF_8)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {                    //4
        cause.printStackTrace();
        ctx.close();
    }
}