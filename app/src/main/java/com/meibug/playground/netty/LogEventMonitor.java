package com.meibug.playground.netty;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.meibug.playground.App;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Created by xing on 16/6/23.
 */
public class LogEventMonitor {
    private WifiManager.MulticastLock multicastLock;
    private final Bootstrap bootstrap;
    private final EventLoopGroup group;

    public LogEventMonitor(InetSocketAddress address) {
        allowMulticast();
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)  //1
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new LogEventDecoder());  //2
                        pipeline.addLast(new LogEventHandler());
                    }
                }).localAddress(address);

    }

    public Channel bind() {
        return bootstrap.bind().syncUninterruptibly().channel();  //3
    }

    public void stop() {
        group.shutdownGracefully();
        multicastLock.release();
    }

    public static void main(int port) throws Exception {
        LogEventMonitor monitor = new LogEventMonitor(new InetSocketAddress(port));  //4
        try {
            Channel channel = monitor.bind();
            System.out.println("LogEventMonitor running");

            channel.closeFuture().await();
        } finally {
            monitor.stop();
        }
    }

    private void allowMulticast(){
        try {
            App app = App.Companion.getInstance();
            WifiManager wifiManager=(WifiManager) app.getSystemService(Context.WIFI_SERVICE);
            multicastLock=wifiManager.createMulticastLock("multicast.test");
            multicastLock.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}