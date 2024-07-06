package top.r3944realms.whimsicality.nettyTest.target.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import top.r3944realms.whimsicality.nettyTest.target.Message.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MessageCodec extends ByteToMessageCodec<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        //1. 4字节的魔数
        out.writeBytes(new byte[] {0x33,0x39,0x34,0x34});
        //2. 1字节的版本
        out.writeByte(1);
        //3. 1字节的序列化方法 jdk 0, json 1
        out.writeByte(0);
        //4. 1字节的指令类型
        out.writeByte(msg.getMessageType());
        //5. 4个字节 请求序号
        out.writeInt(msg.getSequenceId());
        // 无意义，对齐填充
        out.writeByte(0xff);

        //6. 获取内容的字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] byteArray = bos.toByteArray();
        //7. 长度
        out.writeInt(byteArray.length);
        //8. 写入内容
        out.writeBytes(byteArray);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNumber = in.readInt();
        byte version = in.readByte();
        byte serializationType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        Message ms = null;
        if(serializationType == 0) {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            ms =  (Message) ois.readObject();

        }
        out.add(ms);
    }
}
