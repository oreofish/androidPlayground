package com.meibug.playground.netty;

import java.net.InetSocketAddress;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

/**
 * Created by xing on 16/6/21.
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {
    private final InetSocketAddress remoteAddress;

    public LogEventEncoder(InetSocketAddress remoteAddress) {  //1
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, LogEvent logEvent, List<Object> out) throws Exception {
        byte[] msg = logEvent.getMsg().get(0).getBytes();
        ByteBuf buf = channelHandlerContext.alloc().buffer(msg.length + 1);
        buf.writeBytes(msg);  //4
        out.add(new DatagramPacket(buf, remoteAddress));  //5
    }
}

