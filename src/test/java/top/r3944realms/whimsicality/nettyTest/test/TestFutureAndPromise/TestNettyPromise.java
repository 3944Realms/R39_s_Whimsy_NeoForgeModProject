package top.r3944realms.whimsicality.nettyTest.test.TestFutureAndPromise;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;

import java.util.concurrent.ExecutionException;

public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1. 准备 EventLoop对象
        EventLoopGroup group = new NioEventLoopGroup();

        //2.可以主动创建 promise,结果容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(group.next());

        new Thread(()->{
            //3. 任意线程执行计算，计划完毕后向 promise 填充结果
            System.out.println("start...");
            try {
                Thread.sleep(1000);
                promise.setSuccess(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
                promise.setFailure(e);
            }

        }).start();
        //4.接收结果线程
        System.out.println("wait for promise...");
        System.out.println("result is " + promise.get());
    }
}
