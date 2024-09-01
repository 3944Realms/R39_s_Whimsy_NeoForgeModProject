package com.r3944realms.whimsy.demo;


import com.r3944realms.dg_lab.websocket.WebSocketClient;
import com.r3944realms.whimsy.api.dg_lab.DGLabApi;

public class DemoClient {
    public static void main(String[] args) {
        try {
            Class.forName("io.netty.handler.codec.http.HttpHeaders");
            System.out.println("HttpHeaders class is available");
        } catch (ClassNotFoundException e) {
            System.out.println("HttpHeaders class is not available");
            e.printStackTrace();
        }
        WebSocketClient.init(DGLabApi.informSupplier, DGLabApi.noticeSupplier, DGLabApi.qrCodeProducer);
        WebSocketClient.enableDemo();
        WebSocketClient.Start();
    }

}
