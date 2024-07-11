package com.r3944realms.whimsy.api.websocket;

import com.r3944realms.whimsy.config.WebSocketServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.util.concurrent.atomic.AtomicBoolean;

public class WebSocketServer {
    private static Thread WebsocketServerThread;
    static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    private static ServerBootstrap serverBootstrap = null;
    private static EventLoopGroup bossGroup = null;
    private static EventLoopGroup workerGroup = null;
    private static Channel serverChannel = null;
    private static final AtomicBoolean isRunning = new AtomicBoolean(false),
                                        isStopping = new AtomicBoolean(false);
    private static final AtomicBoolean isDemo = new AtomicBoolean(false);
    public static final AtomicBoolean iSDaemonThread = new AtomicBoolean(false);
    public static void enableDemo() {
        isDemo.set(true);
    }
    public static void Start() {
        if(isRunning.get()) {
            logger.info("Server is already running");
            return;
        }
        if(isStopping.get()) {
            logger.info("Server is stopping");
            return;
        }
        initThread(iSDaemonThread.get());
        WebsocketServerThread.start();

    }

    private static void initThread(boolean DaemonThreadEnable) {
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
            int port = isDemo.get() ? 9000 : WebSocketServerConfig.WebSocketServerPort.get();
            logger.debug("WebSocketServer try binding port ... ");
            ChannelFuture channelFuture = serverBootstrap.bind(port);
            channelFuture.sync();
            serverChannel = channelFuture.channel();
            logger.info("WebSocketServer start on the port of {}", port);
            logger.debug("WebSocketServer listening on port {}", port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("WebSocketServer get a error:{}",e.getMessage());
        } finally {
            Stop();
        }});
        WebsocketServerThread.setDaemon(DaemonThreadEnable);
    }

    //
    public static void Stop() {
        if (!isRunning.get()) {
            logger.info("Server is already stopped");
            return;
        }
        if(isStopping.get()) {
            logger.info("Server is stopping,don't stop duplicated");
            return;
        }
        logger.debug("WebSocketServer is stopping...");

        isStopping.set(true);
        try {
            // Close the server channel if it's open
            if (serverChannel != null && serverChannel.isOpen()) {
                serverChannel.close().addListener(future -> {
                    if (future.isSuccess()) {
                        logger.info("Server channel closed successfully");
                    } else {
                        logger.error("Failed to close server channel", future.cause());
                    }
                    serverChannel = null;
                });
            }

            // Shutdown the event loop groups
            if (bossGroup != null) {
                bossGroup.shutdownGracefully().addListener(future -> {
                    if (future.isSuccess()) {
                        logger.info("Boss group shutdown successfully");
                    } else {
                        logger.error("Failed to shutdown boss group", future.cause());
                    }
                    bossGroup = null;
                });
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully().addListener(future -> {
                    if (future.isSuccess()) {
                        logger.info("Worker group shutdown successfully");
                    } else {
                        logger.error("Failed to shutdown worker group", future.cause());
                    }
                    workerGroup = null;
                });
            }
        } finally {
            // Set the server to stopped
            isRunning.set(false);
            logger.info("WebSocketServer Stopped");
        }
    }
    public static boolean isRunning() {
        return isRunning.get();
    }
    public static boolean isStopping() {
        return isStopping.get();
    }
    public static void refresh() {
        if(isStopping.get()) {
            if(workerGroup == null && bossGroup == null && serverChannel == null) {
                isStopping.set(false);
                serverBootstrap = null;
            }
        }
    }
}
