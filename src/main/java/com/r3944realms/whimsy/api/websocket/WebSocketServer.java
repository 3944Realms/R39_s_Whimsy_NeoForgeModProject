package com.r3944realms.whimsy.api.websocket;

import com.r3944realms.whimsy.config.WebSocketConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class WebSocketServer {
    private static Thread WebsocketServerThread;
    static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    private static ServerBootstrap serverBootstrap = null;
    private static EventLoopGroup bossGroup = null;
    private static EventLoopGroup workerGroup = null;
    private static Channel serverChannel = null;
    private static final AtomicBoolean isRunning = new AtomicBoolean(false),
                                        isStopping = new AtomicBoolean(false);
    public static void Start() {
        if(isRunning.get()) {
            log.info("Server is already running");
            return;
        }
        initThread();
        WebsocketServerThread.start();

    }

    private static void initThread() {

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        WebsocketServerThread = new Thread(() -> {
        try {
            isRunning.set(true);
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast();
                }
            });
            log.debug("WebSocketServer try binding port ... ");
            ChannelFuture channelFuture = serverBootstrap.bind(WebSocketConfig.WebSocketServerPort.get());
            channelFuture.sync();
            serverChannel = channelFuture.channel();
            log.info("WebSocketServer start on the port of {}", WebSocketConfig.WebSocketServerPort.get());
            log.debug("WebSocketServer listening on port {}", WebSocketConfig.WebSocketServerPort.get());
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("WebSocketServer get a error:{}",e.getMessage());
        } finally {
            Stop();
            log.info("WebSocketServer Stopped");
        }});
        WebsocketServerThread.setDaemon(true);
    }

    //
    public static void Stop() {
        if(!isRunning.get()) {
            log.info("Server is already stopped");
            return;
        }
        log.debug("WebSocketServer is stopping...");
        isStopping.set(true);
        Future<Integer> future = new NioEventLoopGroup().next().submit(() -> {
            try {
                if (serverChannel != null && serverChannel.isOpen()) {
                    serverChannel.close();
                }
                if (bossGroup != null) {
                    bossGroup.shutdownGracefully();
                }
                if (workerGroup != null) {
                    workerGroup.shutdownGracefully();
                }
            } finally {
                serverBootstrap = null;
                bossGroup = null;
                workerGroup = null;
                serverChannel = null;
                isRunning.set(false);
            }
            return 0;
        });
        try {
            if(future.get() == 0){
                log.info("WebSocketServer has stopped Successfully");

            }
        } catch (InterruptedException | ExecutionException e) {
            log.debug("WebSocketServer stopped error:{}",e.getMessage());
        } finally {
            isStopping.set(false);
        }

    }
    public static boolean isRunning() {
        return isRunning.get();
    }
    public static boolean isStopping() {
        return isStopping.get();
    }
}
