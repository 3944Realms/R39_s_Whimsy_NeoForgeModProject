package top.r3944realms.whimsicality.nettyTest.test.TestFutureAndPromise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class TestJdkFuture {
    private static final Logger log = LoggerFactory.getLogger(TestJdkFuture.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return 10;
            }
        });
        //3.主线程通过future 来获取结果
        System.out.println("future.get() = "+ future.get());
    }
}
