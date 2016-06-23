package com.meibug.playground.netty;

import android.util.Log;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by oreofish on 16/6/22.
 */
public class EchoServer implements Runnable{

    private final int port;
    private static NioEventLoopGroup group = new NioEventLoopGroup(1);

    public EchoServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)                                //4
                    .channel(NioServerSocketChannel.class)        //5
                    .localAddress(new InetSocketAddress(port))    //6
                    .childHandler(new ChannelInitializer<SocketChannel>() { //7
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new EchoServerHandler());
                        }
                    });

            ChannelFuture f = b.bind().sync();            //8
            Log.w("EchoServer " + Thread.currentThread().getId(), EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());
            // f.channel().closeFuture().sync();            //9
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
/*            try {
                group.shutdownGracefully().sync();            //10
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

}
