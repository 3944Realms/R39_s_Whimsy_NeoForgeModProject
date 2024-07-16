package com.r3944realms.whimsy.utils.Network;

import com.google.gson.Gson;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public interface Serializer {
    //反序列化方法
    <T> T deserialize(Class<T> qClass, byte[] bytes);
    //序列化方法
    <T> byte[] serialize(T qObject);

    enum Algorithm implements Serializer {
        Java {
            @SuppressWarnings("unchecked")
            @Override
            public <T> T deserialize(Class<T> qClass, byte[] bytes) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("反序列化失败",e);
                }
            }

            @Override
            public <T> byte[] serialize(T qObject) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(qObject);
                    return bos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败",e);
                }
            }
        },
        /**
         * request UTF-8
         */
        Json {
            @Override
            public <T> T deserialize(Class<T> qClass, byte[] bytes) {
                String json = new String(bytes, StandardCharsets.UTF_8);
                return new Gson().fromJson(json, qClass);
            }

            @Override
            public <T> byte[] serialize(T qObject) {
                String json = new Gson().toJson(qObject);
                return json.getBytes(StandardCharsets.UTF_8);
            }
        },
    }

}
