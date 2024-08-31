package com.r3944realms.dg_lab.websocket;


import com.google.common.collect.Maps;
import com.r3944realms.dg_lab.websocket.message.data.PowerBoxData;
import com.r3944realms.dg_lab.websocket.message.role.WebSocketServerRole;
import com.r3944realms.dg_lab.websocket.protocol.HttpRequestHandler;
import com.r3944realms.dg_lab.websocket.protocol.ServerMessageDataTextWebsocketHandler;
import com.r3944realms.dg_lab.websocket.protocol.ServerMessageTextWebsocketHandler;
import com.r3944realms.whimsy.config.WebSocketServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

public class WebSocketServer {
    //
    public static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static final Map<String, String> channelIdMap = Maps.newConcurrentMap();
    public static final WebSocketServerRole SOCKET_SERVER_ROLE = new WebSocketServerRole("IWebsocketServer");
    //**targetId
    public static final Map<String, PowerBoxData> powerBoxDataMap = Maps.newConcurrentMap();

    // 储存已连接的用户及其标识
    public static final Map<String, ChannelHandlerContext> connections = Maps.newConcurrentMap();
    // 存储消息关系
    public volatile static Map<String, String> relations = Maps.newConcurrentMap();
    // 存储定时器
    public volatile static Map<String, Timer> clientTimers = Maps.newConcurrentMap();

    //默认发送时间1秒
    public static final Integer punishmentDuration = 5;
    // 默认一秒发送1次
    public static final Integer punishmentTime = 1;
    // 心跳定时器
    public static Timer heartTimer = null;
    //
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
    public static final AtomicBoolean isTextMessageMode = new AtomicBoolean(false);
    public static void enableDemo() {
        isDemo.set(true);
    }
    public static void Start() {
        refresh();
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
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                    pipeline.addLast(new HttpServerCodec());
                    pipeline.addLast(new HttpObjectAggregator(65536));
                    pipeline.addLast(new HttpRequestHandler());//去除路径请求，APP会发送带路径请求的HTTP升级请求，目前用不到
                    pipeline.addLast("WSP",new WebSocketServerProtocolHandler("/"));
                    pipeline.addLast(new WebSocketFrameAggregator(65536));
                    pipeline.addLast(isTextMessageMode.get() ?
                            new ServerMessageTextWebsocketHandler() :
                            new ServerMessageDataTextWebsocketHandler()
                    );
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
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
            logger.error("WebSocketServer interrupted: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("WebSocketServer get a error:{}",e.getMessage());
        } finally {
            Stop();
        }});
        WebsocketServerThread.setDaemon(DaemonThreadEnable);
    }

    //
    public static void Stop() {
        refresh();
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
            // Cancel the heartbeat timer
            if(heartTimer != null) {
                heartTimer.cancel();
                heartTimer = null;
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
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
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
