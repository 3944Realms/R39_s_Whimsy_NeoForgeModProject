package top.r3944realms.whimsicality.nettyTest.test.TestEventLoop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;


public class EventLoopServer {
    public static void main(String[] args) {

        //细分2: 创建一个独立的 EventLoopGroup
        EventLoopGroup group = new DefaultEventLoop();
        new ServerBootstrap()
                // boss 和 worker
                //细分1： Boss只负责 accept事件 ，worker 只负责 socketChannel 上的读写
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());//将 ByteBuf 转换为字符串
                        ch.pipeline().addLast("handler1", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                ByteBuf byteBuf = ((ByteBuf)msg);
//                                System.out.println(byteBuf.toString(StandardCharsets.UTF_8));
                                ctx.fireChannelRead(msg);//将消息传递给下一个handler
                            }
                        }).addLast(group, "handler2", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(9000);

        System.out.println("Server started on port 9000");
    }
}
