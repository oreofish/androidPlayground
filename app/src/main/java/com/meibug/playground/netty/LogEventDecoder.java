package com.meibug.playground.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

/**
 * Created by xing on 16/6/23.
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
        ByteBuf data = datagramPacket.content();
        String line = data.toString(CharsetUtil.UTF_8);

        LogEvent event = new LogEvent(datagramPacket.recipient(), System.currentTimeMillis(), line);

        out.add(event);
    }
}