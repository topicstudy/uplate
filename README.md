## 该model的功能是测U盘容量
该软件不能做成BS，因为:
 * 服务器无法直接操作客户端电脑(如直接将文件写入客户端电脑，需要用户手动点)
 * 大文件由服务器写入客户端需要很长时间

## 下载
 * src/main/resources下可看到exe文件
 * [下载测U盘真实容量.exe](https://github.com/luotuoshamo/uplate/tree/master/src/main/resources/)

## 待改进
 * icon图标无法显示
 * 测量结束后没结束掉所有线程
 * 线程体时死循环时无法感知外界数据的变化，必须要Thread.sleep