package com.r3944realms.whimsy.api.websocket;

import com.r3944realms.whimsy.api.websocket.protocol.ClientMessageWebsocketHandler;
import com.r3944realms.whimsy.utils.NetworkUtils.UrlValidator;
import com.r3944realms.whimsy.utils.Transform.StringHandlerUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
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
    private static final AtomicBoolean isDemo = new AtomicBoolean(false);
    public static final AtomicBoolean iSDaemonThread = new AtomicBoolean(false);
    public static void enableDemo() {
        isDemo.set(true);
        address = "127.0.0.1";

        port = 9000;
    }
    public static String getUrl() {
        return StringHandlerUtil.buildWebSocketURL(address, port, false);
    }

    public static void syncServerData(String address, int port) {
        if(UrlValidator.isValidAddress(address) && port != -1) {
            WebSocketClient.address = address;
            WebSocketClient.port = port;
            hasSync.set(true);
        }
    }
    public static void Start() {

        if(isRunning.get()) {
            logger.info("WebSocketClient is already running");
            return;
        }else if(!hasSync.get() && !isDemo.get()) {
            logger.info("waiting for sync.");
            return;
        } else if (isStopping.get()) {
            if(eventLoopGroup != null && channel != null) {
                logger.info("WebSocketClient is stopping");
                return;
            }
            isStopping.set(false);
        }
        initThread(iSDaemonThread.get());

        WebSocketClientThread.start();
    }
    public static void initThread(boolean DaemonThreadEnable) {
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
                        pipeline.addLast(
                                new LoggingHandler(LogLevel.DEBUG),
                                new HttpClientCodec(),
                                new HttpObjectAggregator(65536),
                                new WebSocketClientProtocolHandler(
                                        WebSocketClientHandshakerFactory.newHandshaker(
                                                URI.create(StringHandlerUtil.buildWebSocketURL(address, port, false)),
                                                WebSocketVersion.V13,
                                                null,
                                                false,
                                                new DefaultHttpHeaders()
                                        )
                                ),
                                new WebSocketFrameAggregator(65536),
                                new ClientMessageWebsocketHandler()
                        );
                    }
                });
                channel = bootstrap.connect(address, port).sync().channel();
                // 等待 channel 关闭
                logger.info("WebSocketClient successfully connected to {}:{}", address, port);
                channel.closeFuture().sync();
            } catch (Exception e) {
                logger.error("WebsocketClient get a error: {}",e.getMessage());
            } finally {
                Stop();
                logger.info("WebSocketClient has stopped");
            }
        });
        WebSocketClientThread.setName("WebSocketClientThread");
        WebSocketClientThread.setDaemon(DaemonThreadEnable);

    }
    public static void Stop() {
        if(!isRunning.get()) {
            logger.info("WebSocketClient is not running");
            return;
        }
        if(isStopping.get()) {
            logger.info("WebSocketClient is stopping,don't stop duplicated");
            return;
        }
        logger.debug("WebSocketClient is stopping");

        isStopping.set(true);
        try{
            if(channel != null) {
                  channel.close().addListener(future -> {
                      if(future.isSuccess()) {
                          logger.info("Client channel closed successfully");
                      } else {
                          logger.error("Client channel close failed", future.cause());
                      }
                      channel = null;
                  });
            }
            if(eventLoopGroup != null) {
                eventLoopGroup.shutdownGracefully().addListener(future -> {
                    if(future.isSuccess()) {
                        logger.info("Client event loop group closed successfully");
                    } else {
                        logger.error("Client event loop group close failed", future.cause());
                    }
                    eventLoopGroup = null;
                });
            }
        } finally {
            isRunning.set(false);
            hasSync.set(false);
        }
    }
    public static boolean isRunning() {
        return isRunning.get();
    }
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isStopping() {
        return isStopping.get();
    }
    public static boolean hasSync() {
        return hasSync.get();
    }
    public static void refresh() {
        if(isStopping.get()) {
            if(eventLoopGroup == null && channel == null ) {
                isStopping.set(false);
                bootstrap = null;
            }
        }
    }

}
