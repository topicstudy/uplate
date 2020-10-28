* 该model的功能是测U盘容量

* 该model不能做成webapp，因为:
。服务器无法操作客户端浏览器
所在的电脑(如**直接**将客户端电脑中的文件从1个文件夹复制到另一个文件夹)
。填充文件是大文件，由服务器写入客户端需要很长时间

* attention
```java
    String s = "zxy";
    change(s);
    System.out.println(s);//zxy
---
    static void change(String s) {
        s = "smlt";
    }
```

* 为什么：
[1.jpg](https://gitee.com/smlt_1_wjh_q/myApp/blob/master/uPlate/src/main/resources/picOfREADME/1.png)


* 下载
[测U盘容量.exe](https://gitee.com/smlt_1_wjh_q/myApp/raw/master/src/main/resources/release/%E6%B5%8BU%E7%9B%98%E5%AE%B9%E9%87%8F.exe)