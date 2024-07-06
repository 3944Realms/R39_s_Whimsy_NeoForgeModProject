package com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Websocket;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.DataContext.PowerBoxData;
import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.DataPayload;
import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.HeartBeatPayload;
import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.Utils.MyPayloadFitClassTransformer;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author vpn_33 (原作者)@link  <a href="https://github.com/Vpn33/DG-LAB-OPENSOURCE/blob/main/socket/BackEnd(java%2BSpringboot)/src/main/java/com/vpn33/websocket/">...</a>
 * @author R3944Realms (修改)
 */
@Component
@ServerEndpoint(value = "/**")
public class WebsocketServer {
    protected static final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);
    // 储存已连接的用户及其标识
    protected static Map<String, Session> connections = Maps.newConcurrentMap();
    // 存储消息关系
    protected static Map<String, String> relations = Maps.newConcurrentMap();
    // 存储定时器
    protected static Map<String, Timer> clientTimers = Maps.newConcurrentMap();
    //Gson（又称Google Gson）是Google公司发布的一个开放源代码的Java库，
    //主要用途为序列化Java对象为JSON字符串，或反序列化JSON字符串成Java对象。
    public static Gson gsonCodec = new GsonBuilder().create();
    //默认发送时间1秒
    public static final Integer punishmentDuration = 5;

    // 默认一秒发送1次
    public static final Integer punishmentTime = 1;

    // 心跳定时器
    Timer heartTimer = null;
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        String clientId = session.getId();
        connections.put(clientId, session);
        logger.info("新的 websocket连接已建立，标识符为{}", session.getId());
        //发送标识符给客户端（格式固定，双方都必须获取才可以进行后续通信：比如浏览器和APP）
        DataPayload dataPayload = new DataPayload("bind",clientId,"");
        ((PowerBoxData)dataPayload.data()).setMessage_A("targetId");
        send(dataPayload);

        // 启动心跳定时器（如果尚未启动）
        if(heartTimer == null) {
            heartTimer = new Timer();
            heartTimer.schedule(new TimerTask() {
               @Override
               public void run() {
                   // 遍历 clients Map（大于0个链接），向每个客户端发送心跳消息
                   if(!connections.isEmpty()) {
                       logger.debug("关系池大小：{}连接池大小：{}发送心跳消息:{}", relations.size(), connections.size(), new Date().toString());
                       for(String clientId : connections.keySet()) {
                           Session client = connections.get(clientId);
                            String targetId = relations.get(clientId);
                            if(ObjectUtils.isEmpty(targetId)) {
                                targetId = "";
                            }
                           HeartBeatPayload heartBeatPayload = new HeartBeatPayload(clientId,targetId);
                            send(client, heartBeatPayload);
                       }
                   }
               }
            },0,60000/*60S = 60000ms*/);// 每分钟发送一次心跳消息
        }

    }
    @OnMessage
    public void onMessage(Session session, String text) {
        logger.debug("收到消息: {}",text);
        DataPayload dataPayload = null;
        try{
            dataPayload = gsonCodec.fromJson(text, DataPayload.class);
        } catch (Exception e) {
            DataPayload errDataPayload = new DataPayload("msg","","");
            ((PowerBoxData)errDataPayload.data()).setMessage_A("403");
            send(session, errDataPayload);
            return;
        }
        //非法信息来源拒绝
        if(!connections.containsKey(session.getId()) && !connections.containsKey(dataPayload.clientID())) {
            DataPayload errDataPayload = new DataPayload("msg","","");
            ((PowerBoxData)errDataPayload.data()).setMessage_A("404");
            send(session, errDataPayload);
            return;
        }
        if(!ObjectUtils.isEmpty(dataPayload.type()) && !ObjectUtils.isEmpty(dataPayload.clientID()) &&
            !ObjectUtils.isEmpty(((PowerBoxData)dataPayload.data()).getMessage_A()) && !ObjectUtils.isEmpty(dataPayload.targetID())) {
            //优先处理绑定关系
            String type = dataPayload.type();
            String clientId = dataPayload.clientID();
            String targetId = dataPayload.targetID();
            String message = ((PowerBoxData)dataPayload.data()).getMessage_A();
            switch (type) {
                case "bind":
                    //服务器下发绑定关系
                    if (connections.containsKey(clientId) && connections.containsKey(targetId)) {
                        //relations的双方均不存在这俩id
                        if (!(relations.containsKey(clientId) || relations.containsKey(targetId) || relations.containsValue(clientId) || relations.containsValue(targetId))) {
                            relations.put(clientId, targetId);
                            Session client = connections.get(clientId);
                            DataPayload sendData = new DataPayload("bind", clientId, targetId);
                            ((PowerBoxData) sendData.data()).setMessage_A("200");
                            send(session, sendData);
                            send(client, sendData);
                        } else {
                            DataPayload data = new DataPayload("bind", clientId, targetId);
                            ((PowerBoxData) data.data()).setMessage_A("400");
                            send(session, data);
                            return;
                        }
                    } else {
                        DataPayload data = new DataPayload("bind", clientId, targetId);
                        ((PowerBoxData) data.data()).setMessage_A("401");
                        send(session, data);
                        return;
                    }
                    break;
                case "1":
                case "2":
                case "3":
                    //服务器下发App强度调节
                    if (relations.containsKey(clientId) && !relations.get(clientId).equals(targetId)) {
                        DataPayload data = new DataPayload("bind", clientId, targetId);
                        ((PowerBoxData) data.data()).setMessage_A("402");
                        send(session, data);
                        return;
                    }
                    if (connections.containsKey(targetId)) {
                        Session client = connections.get(targetId);
                        int dataType = Integer.parseInt(dataPayload.type());
                        int sendType = dataType - 1;
                        int sendChannel = null != ((PowerBoxData) dataPayload.data()).getChannel() ? ((PowerBoxData) dataPayload.data()).getChannel() : 1;
                        int sendStrength = dataType >= 3 ? ((PowerBoxData) dataPayload.data()).getStrength() : 1;
                        String msg = "strength-" + sendChannel + "+" + sendType + "+" + sendStrength;
                        DataPayload sendData = new DataPayload("msg", clientId, targetId);
                        ((PowerBoxData) sendData.data()).setMessage_A(msg);
                        send(session, sendData);
                    }
                    break;
                case "4":
                    //服务器下发指定App强度
                    if (relations.containsKey(clientId) && !relations.get(clientId).equals(targetId)) {
                        DataPayload data = new DataPayload("bind", clientId, targetId);
                        ((PowerBoxData) data.data()).setMessage_A("402");
                        send(session, data);
                        return;
                    }
                    if (connections.containsKey(targetId)) {
                        Session client = connections.get(targetId);
                        DataPayload sendData = new DataPayload("msg", clientId, targetId);
                        ((PowerBoxData) sendData.data()).setMessage_A(message);
                        send(session, sendData);
                    }
                    break;
                case "clientMsg":
                    //服务端下发给客户的信息
                    if (relations.containsKey(clientId) && !relations.get(clientId).equals(targetId)) {
                        DataPayload data = new DataPayload("bind", clientId, targetId);
                        ((PowerBoxData) data.data()).setMessage_A("402");
                        send(session, data);
                        return;
                    }
                    if (ObjectUtils.isEmpty(((PowerBoxData) dataPayload.data()).getMessage_B())) {
                        DataPayload data = new DataPayload("error", clientId, targetId);
                        ((PowerBoxData) data.data()).setMessage_A("501");
                        send(session, data);
                        return;
                    }
                    if (connections.containsKey(targetId)) {
                        Integer sendTimeA = null != ((PowerBoxData) dataPayload.data()).getTimer_A() ? ((PowerBoxData) dataPayload.data()).getTimer_A() : punishmentDuration;
                        Integer sendTimeB = null != ((PowerBoxData) dataPayload.data()).getTimer_B() ? ((PowerBoxData) dataPayload.data()).getTimer_B() : punishmentDuration;
                        Session target = connections.get(targetId);
                        DataPayload sendDataA = new DataPayload("msg", clientId, targetId);
                        ((PowerBoxData) sendDataA.data()).setMessage_A("pulse-" + ((PowerBoxData) dataPayload.data()).getMessage_A());
                        DataPayload sendDataB = new DataPayload("msg", clientId, targetId);
                        ((PowerBoxData) sendDataB.data()).setMessage_A("pulse-" + ((PowerBoxData) dataPayload.data()).getMessage_B());
                        int totalSendsA = punishmentTime * sendTimeA;
                        int totalSendsB = punishmentTime * sendTimeB;
                        Integer timeSpace = 1000 / punishmentTime;

                        logger.debug("消息发送中，总消息数A：{}总消息数B：{}持续时间A：{}持续时间B：{}", totalSendsA, totalSendsB, sendTimeA, sendTimeB);
                        if (clientTimers.containsKey(clientId)) {
                            // 计时器尚未工作完毕, 清除计时器且发送清除APP队列消息，延迟150ms重新发送新数据
                            // 新消息覆盖旧消息逻辑
                            session.getAsyncRemote().sendText("当前有正在发送的消息，覆盖之前的消息");

                            Timer timeId = clientTimers.get(clientId);
                            clearInterval(clientId, timeId);//清除定时器
                            clientTimers.remove(clientId);//清除 Map 中的对应项

                            //发送APP波形队列清除指令
                            DataPayload clearDataA = new DataPayload("msg", clientId, targetId);
                            ((PowerBoxData) clearDataA.data()).setMessage_A("clear-1");
                            DataPayload clearDataB = new DataPayload("msg", clientId, targetId);
                            ((PowerBoxData) clearDataB.data()).setMessage_A("clear-2");
                            send(session, clearDataA);
                            send(session, clearDataB);
                            try {
                                Thread.sleep(150);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            delaySendMsg(clientId, session, target, sendDataA, sendDataB, totalSendsA, totalSendsB, timeSpace);
                        } else {
                            // 不存在未发完的消息 直接发送
                            delaySendMsg(clientId, session, target, sendDataA, sendDataB, totalSendsA, totalSendsB, timeSpace);
                        }
                    } else {
                        logger.debug("未找到匹配的客户端，clientId:{}", clientId);
                        DataPayload data = new DataPayload("msg", clientId, targetId);
                        ((PowerBoxData) data.data()).setMessage_A("404");
                        send(session, data);
                        return;
                    }
                    break;
                default:
                // 未定义的普通消息
                    if (relations.containsKey(clientId) && !relations.get(clientId).equals(targetId)) {
                        DataPayload data = new DataPayload("bind", clientId, targetId);
                        ((PowerBoxData)data.data()).setMessage_A("402");
                        send(session, data);
                        return;
                    }
                    if(connections.containsKey(clientId)) {
                        Session client = connections.get(clientId);
                        DataPayload sendData = new DataPayload(dataPayload.type(), clientId, targetId);
                        ((PowerBoxData)sendData.data()).setMessage_A(((PowerBoxData) dataPayload.data()).getMessage_A());
                        send(session, sendData);
                    }
                    else {
                        // 未找到匹配的客户端
                        DataPayload data = new DataPayload("msg", clientId, targetId);
                        ((PowerBoxData)data.data()).setMessage_A("404");
                        send(session, data);
                    }
                    break;
            }
        }
    }

    /**
     * 关闭连接
     */
    @SuppressWarnings("LoggingSimilarMessage")
    @OnClose
    public void onClose(Session session) throws IOException {
        //连接关闭时，清除对应的clientId 和 Websocket 实例
        logger.debug("Websocket 连接已关闭");
        //遍历 clients Map, 找到并删除对应的 clientId 实例
        String clientId = session.getId();
        logger.debug("断开的client Id:{}", clientId);
        for(String key : relations.keySet()) {
            String value = relations.get(key);
            if(key.equals(clientId)) {
                //网页端断开 通知app
                Session appClient = connections.get(value);
                DataPayload data = new DataPayload("break", clientId, value);
                ((PowerBoxData)data.data()).setMessage_A("209");
                send(session, data);
                appClient.close();
                relations.remove(key);
                logger.debug("对方掉线，关闭{}", value);
            } else if(value.equals(clientId)) {
                //app断开 通知网页
                Session webClient = connections.get(key);
                DataPayload data = new DataPayload("break", clientId, value);
                ((PowerBoxData)data.data()).setMessage_A("209");
                send(webClient, data);
                webClient.close();
                relations.remove(key);
                logger.debug("对方掉线，关闭:{}",clientId);
            }
        }
        Session removedSession = null;
        //noinspection TryFinallyCanBeTryWithResources
        try {
          removedSession = connections.remove(clientId);//清除websocket客户端
        } catch (Exception e){
            logger.debug("捕获异常:{}",e.getMessage());
        }finally {
            if(removedSession != null) {
                removedSession.close();
            }
        }
        logger.debug("已清除clientId:{}，当前size:{}", clientId, connections.size());
    }
    /**
     * 异常处理
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        // java.io.IOException: 远程主机强迫关闭了一个现有的连接。 这个错误是由于连接直接被关闭 无需处理 依然会自动回调到onClose关闭
        if(throwable instanceof IOException) {
            return;
        }
        logger.error("Websocket 异常{}",throwable.getMessage());
        //在此通知用户异常，通过websocket 发送消息给双方
        String clientId = session.getId();
        if(ObjectUtils.isEmpty(clientId)) {
            logger.debug("无法找到对应的clientId");
            return;
        }
        //构造错误消息
        String errorMsg = "WebSocket 异常:" + throwable.getMessage();
        for(String key : relations.keySet()) {
            //遍历关系 Map， 找到并通知没有掉线的一方、
            String value = relations.get(key);
            if(key.equals(clientId)) {
                //通知 app
                String appid = relations.get(key);
                Session appClient = connections.get(appid);
                DataPayload data = new DataPayload("error", clientId, value);
                ((PowerBoxData)data.data()).setMessage_A("500");
                send(appClient, data);
            } else if(value.equals(clientId)) {
                //通知网页
                Session webClient = connections.get(key);
                DataPayload data = new DataPayload("error", clientId, value);
                ((PowerBoxData)data.data()).setMessage_A(errorMsg);
                send(webClient, data);
            }
        }
    }

    private void send(DataPayload dataPayload) {
        Session session = connections.get(dataPayload.clientID());
        send(session,dataPayload);
    }
    private void send(HeartBeatPayload heartBeatPayload) {
        Session session = connections.get(heartBeatPayload.clientID());
        send(session,heartBeatPayload);
    }
    private void send(Session session,DataPayload dataPayload) {
        session.getAsyncRemote().sendText(gsonCodec.toJson(MyPayloadFitClassTransformer.transform(dataPayload)));
    }
    private void send(Session session, HeartBeatPayload heartBeatPayload) {
        session.getAsyncRemote().sendText(gsonCodec.toJson(MyPayloadFitClassTransformer.transform(heartBeatPayload)));
    }
    private void send(Session session, String message) {
        session.getAsyncRemote().sendText(message);
    }
    public void clearInterval(String clientId, Timer timer) {
        timer.cancel();
        clientTimers.remove(clientId); // 删除对应的定时器
        timer = null;
    }
    public void delaySendMsg(String clientId, Session client, Session target, DataPayload sendDataA, DataPayload sendDataB, Integer totalSendsA, Integer totalSendsB, Integer timeSpace) {
        // 发信计时器 AB通道会分别发送不同的消息和不同的数量 必须等全部发送完才会取消这个消息 新消息可以覆盖
        send(target, sendDataA);//立即发送一次AB通道的消息
        send(target, sendDataB);
        totalSendsA--;
        totalSendsB--;
        if (totalSendsA > 0 || totalSendsB > 0) {
            Timer timer = new Timer();
            DG_LAB_TimerTask task = new DG_LAB_TimerTask(client, target, sendDataA, sendDataB, totalSendsA, totalSendsB, s -> {
                clearInterval(clientId, timer);
            });
            timer.scheduleAtFixedRate(task, 0, timeSpace.longValue());// 每隔频率倒数触发一次定时器
            // 存储clientId与其对应的timerId
            clientTimers.put(clientId, timer);
        }
    }
}
