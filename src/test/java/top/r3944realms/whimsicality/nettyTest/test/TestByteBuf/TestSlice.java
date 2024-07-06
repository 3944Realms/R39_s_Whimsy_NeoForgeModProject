package top.r3944realms.whimsicality.nettyTest.test.TestByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[] { 'A', 'B', 'C', 'D', 'E' ,'F', 'G', 'H', 'I', 'J'});
        TestByteBuf.log(buf);

        //在切片过程中没有发生数据复制
        ByteBuf f1 = buf.slice(0, 5);
        ByteBuf f2 = buf.slice(5, 5);
        TestByteBuf.log(f1);
        TestByteBuf.log(f2);
        System.out.println("================");
        f1.setByte(1,'b');
        TestByteBuf.log(buf);
        TestByteBuf.log(f1);
        TestByteBuf.log(f2);
    }

}
