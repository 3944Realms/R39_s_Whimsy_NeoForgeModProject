package top.r3944realms.whimsicality.nettyTest.test.TestEventLoop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class EventLoopClient {
     public static void main(String[] args) throws InterruptedException {
         // 带有 Future，Promise 的类型都是和异步方法配合使用，用来处理结果
         NioEventLoopGroup group = new NioEventLoopGroup();
         ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                //3. 选择客户端Channel实现
                .channel(NioSocketChannel.class)
                //4. 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override // 在连接建立后被调用
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());//把 hello，转为ByteBuf
                    }
                })
                //5. 连接到服务器
                // 异步非阻塞，main 发起了调用，真正执行 connect 是 nio 线程
                .connect("localhost", 9000);
//         //方法1 使用 sync 方法来同步处理结果
//         channelFuture.sync(); //阻塞当前线程，直到 nio 线程连接建立完毕
//         // 无阻塞向下执行获取 channel
          // 2.2 使用addListen（回调对象） 方法异步处理结果
         channelFuture.addListener((ChannelFutureListener) future -> {
             Channel channel = future.channel();
         });
         Channel channel = channelFuture.channel();
         new Thread(() -> {
             Scanner scanner = new Scanner(System.in);
             while (true) {
                String s = scanner.nextLine();
                if("q".equals(s)) {
                    channel.close();
                    break;
                } else {

                    channel.writeAndFlush(s);
                }
             }
         }).start();
         System.out.println(channelFuture);
//         //获取 CloseFuture 1) 同步处理关闭 2) 异步处理关闭
         ChannelFuture closeFuture = channel.closeFuture();
//         closeFuture.sync();
         closeFuture.addListener((ChannelFutureListener) future -> {
             System.out.println("Channel closed");
             group.shutdownGracefully();
         });
        System.out.println("here");
    }
}
