![Whimsicality Minecraft Mod](https://github.com/3944Realms/R39_s_Whimsy_NeoForgeModProject/blob/master/src/main/resources/whimsicalityLogo.png?raw=true)
# Project - WhimsicalityMod Neoforge

## 项目概述

本项目基于1.20.6~1.21 Neoforge，目标是基于原版和第三方动画库(Geckolib提高玩家间的互动性

## 项目大致阶段

## 大致方向

* Geckolib动画->Mix掉原版部分动画
* Mix原版聊天系统

# 计划2024.06.29-2024.07中期

第一阶段完成情况（Netty网络处理器handler部分没想好怎么个流程，且在读websocket协议部分

但关于项目中一个二维码生成方式已经确定并实现了部分



* 使用Minecraft网络基于的Netty框架来实现websocket服务
  - [x] Websocket服务器启动
  - [ ] 服务器逻辑编写
  - [ ] Websocket客户端逻辑编写
  - [ ] 实现服务器与客户端通讯
* 实现聊天区域半径设置
  - [x] 了解原版聊天逻辑
  - [x] Mixin原版原版聊天系统
  - [x] 处理聊天网络发包方面逻辑
  - [x] 可以使用指令、配置设置聊天半径

