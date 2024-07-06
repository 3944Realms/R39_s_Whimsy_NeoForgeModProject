package top.r3944realms.whimsicality.nettyTest.test.echoMode;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class EchoClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Channel channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                System.out.println( "[getMessage From Server] "+ buf.toString(StandardCharsets.UTF_8));
                            }
                        });
                    }
        }).connect("127.0.0.1", 9000).sync().channel();

        channel.closeFuture().addListener(future -> {
           group.shutdownGracefully();
        });
        new Thread(() -> {
           Scanner scanner = new Scanner(System.in);
           while (true) {
               String line = scanner.next();
               if("quit".equals(line)) {
                   channel.close();
                   break;
               }
               channel.writeAndFlush(line);
           }
        }).start();
    }
}
