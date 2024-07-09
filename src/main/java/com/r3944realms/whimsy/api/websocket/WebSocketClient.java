package com.r3944realms.whimsy.api.websocket;

import com.r3944realms.whimsy.utils.NetworkUtils.AddressValidator;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
//通过服务器发包来告诉有效链接
public class WebSocketClient {
    private static Thread WebSocketClientThread;
    static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class);
    private static Bootstrap bootstrap = null;
    private static EventLoopGroup eventLoopGroup = null;
    private static Channel channel = null;
    private static volatile String address = null;
    private static volatile int port = -1;
    private static final AtomicBoolean isRunning = new AtomicBoolean(false),
                                        isStopping = new AtomicBoolean(false),
                                        hasSync = new AtomicBoolean(false);

    public static void syncServerData(String address, int port) {
        if(AddressValidator.isValidAddress(address) && port != -1) {
            WebSocketClient.address = address;
            WebSocketClient.port = port;
            hasSync.set(true);
        }
    }
    public static void Start() {
        if(isRunning.get()) {
            logger.info("WebSocketClient is already running");
            return;
        }else if(!hasSync.get()) {
            logger.info("waiting for sync.");
            return;
        } else if (isStopping.get()) {
            logger.info("WebSocketClient is stopping");
            return;
        }
        initThread();

        WebSocketClientThread.start();

    }
    public static void initThread() {
        eventLoopGroup = new NioEventLoopGroup();
        WebSocketClientThread = new Thread(() -> {
            try {
                isRunning.set(true);
                bootstrap = new Bootstrap();
                bootstrap.group(eventLoopGroup);
                bootstrap.channel(NioSocketChannel.class);
                bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast();
                    }
                });
                channel = bootstrap.connect(address, port).sync().channel();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                Stop();
                logger.info("WebSocketClient has stopped");
            }
        });
        WebSocketClientThread.setDaemon(true);
    }
    public static void Stop() {
        if(!isRunning.get()) {
            logger.info("WebSocketClient is not running");
            return;
        }
        logger.debug("WebSocketClient is stopping");
        isStopping.set(true);
        Future<Integer> future = new NioEventLoopGroup().next().submit(() -> {
            try {
                if(channel != null && channel.isOpen()) {
                    channel.close().sync();
                }
                if(eventLoopGroup != null) {
                    eventLoopGroup.shutdownGracefully().sync();
                }
            } finally {
                eventLoopGroup = null;
                channel = null;
                bootstrap = null;
                isRunning.set(false);
                address = "";
                port = -1;
                hasSync.set(false);
            }
            return 0;
        });
        try {
            if(future.get() == 0) {
                logger.info("WebSocket Client has stopped Successfully");
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("WebSocket Client stooped error:{}",e.getMessage());
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
    public static boolean hasSync() {
        return hasSync.get();
    }
}
