# 2024/07/06 - 2024/07/10

## 当前学习目标 

Netty使用 与 Java并发进一步学习

# 2024/07/05

##  当前学习目标 

1. Netty

# 2024/07/04

##  当前学习目标 

1. NIO- > Netty

# 2024/07/03

##  当前学习目标 

1. NIO

# 2024/07/02

##  当前学习目标 

1. Netty线程模型

## 放弃 

放弃基于Spring Boot构建，原因：不确定因素太多，配置麻烦，目前水平不够

# 2024/07/01

箴言：一个知识越贫乏的人，越是拥有一种莫名奇怪的勇气和自豪感。因为知识越贫乏，你所相信的东西就越绝对，因为你根本没有听过与此相对立的观点。
## 当前学习目标
1.  Gradle构建工具

# 2024/06/30
## 已完成的进度
1. 将基于Java+SpringBoot的DG-LAB websocket服务部分写入完毕
2. 获得了一些模组依赖的外置库jar包
## 遇到的问题 
1. SpringBoot项目服务不能正常启动
2. 还未能构建出一个合适的Build配置来使所需依赖库被正确打包进最终jar中（至少目前不能成功的加载进去）
（shadowJar配置不清楚，弄出来的jar过于巨大，存在很多问题）
## 借鉴方案 
1. JSON配置相关依赖库信息(如下)
````
{
  "jars": [
    {
      "identifier": {
        "group": "io.github.llamalad7",
        "artifact": "mixinextras-forge"
      },
      "version": {
        "range": "[0.3.5,)",
        "artifactVersion": "0.3.5"
      },
      "path": "META-INF/jarjar/mixinextras-forge-0.3.5.jar",
      "isObfuscated": false
    },
    {
      "identifier": {
        "group": "curse.maven",
        "artifact": "geckolib-388172"
      },
      "version": {
        "range": "[4181370]",
        "artifactVersion": "4181370"
      },
      "path": "META-INF/jarjar/geckolib-388172-4181370.jar",
      "isObfuscated": false
    },
    ...//此处省略
  ]
}
````
> 可能是通过脚本生成的JSON文件，通过该JSON来决定如何加载依赖
2. 通过自动检查机制，来检查是否缺少对应依赖，如果发现缺少，则自动通过网络途径来下载对应缺失依赖