package top.r3944realms.whimsicality.nettyTest.advanced;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static top.r3944realms.whimsicality.nettyTest.advanced.HelloWorldClient.sLog;

public class HelloWorldServer {
    static final Logger log = LoggerFactory.getLogger(HelloWorldServer.class);
    void start() {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.option(ChannelOption.SO_RCVBUF,10);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            sLog("connected " + ctx.channel());
                            super.channelActive(ctx);
                        }

                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                            sLog("disconnect " +  ctx.channel());
                            super.channelInactive(ctx);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            super.channelRead(ctx, msg);
                        }
                    });
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(9000);
            sLog(channelFuture.channel()+ " binding...");
            channelFuture.sync();
            sLog(channelFuture.channel() + " bound...");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            sLog("server error" + e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            sLog("stoped");
        }
    }

    public static void main(String[] args) {
        new HelloWorldServer().start();
    }
}
