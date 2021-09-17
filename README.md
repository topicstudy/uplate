# 介绍
* 该软件的功能是测U盘容量

  

* 该软件不能做成BS，因为:
 1. 服务器无法直接操作客户端电脑(如直接将文件写入客户端电脑，需要用户手动点)
 2. 大文件由服务器写入客户端需要很长时间

# 使用

环境要求：

| 环境  | 版本  |
| ----- | ----- |
| Java  | 8     |
| maven | 3.5.4 |



进入项目根目录中（能看到pom.xml），打开cmd运行：

```shell
mvn clean
mvn package
```

等待运行完成，会在项目根目录中看到target文件夹，点进去，然后运行：

```shell
cd ./target
java -classpath uplate-xxx.jar com.wjh.ui.Window
```

即可出现如下页面：

![image-20210918002138500](README.assets\image-20210918002138500.png)