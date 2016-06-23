package com.meibug.playground.netty;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Created by xing on 16/6/21.
 */

public class LogEventBroadcaster {
    private final Bootstrap bootstrap;
    private final EventLoopGroup group;
    private Channel ch;

    public LogEventBroadcaster(InetSocketAddress address) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address)); //1

        ch = bootstrap.bind(0).syncUninterruptibly().channel();
        System.out.println("LogEventBroadcaster running");
    }

    public void search() {
        ch.writeAndFlush(new LogEvent(null, -1, "file"));
    }

    public void stop() {
        group.shutdownGracefully();
    }
}