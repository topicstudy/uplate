package com.wjh.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 文件相关的工具类
 */
public class FileUtil {
    /**
     * 删除文件或文件夹
     *
     * @param path 例如：d:/tmp   d:/    d:/x.txt
     */
    public static void delete(String path) {
        File file = new File(path);
        if (file.isFile()) {//文件可直接删除
            file.delete();
        } else if (file.isDirectory()) {//空文件夹才能直接删除
            String[] subFileNames = file.list();//文件夹file中的文件、文件夹
            if (subFileNames == null) {//文件不存在或其它错误
                return;
            }
            //空文件夹可直接删除
            if (subFileNames.length == 0) {
                file.delete();
            }
            //清空文件夹中的内容
            for (String subFileName : subFileNames) {
                String absolutePath = file.getAbsolutePath();
                absolutePath = absolutePath.endsWith("/") || absolutePath.endsWith("\\") ? absolutePath : absolutePath + "/";

                String subFilePath = absolutePath + subFileName;
                delete(subFilePath);
            }
            //删除空文件夹
            file.delete();
        }
    }

    /**
     * 判断文件夹是否是空的
     *
     * @return 文件夹为空返回true
     */
    public static boolean isEmptyDir(String dirPath) throws Exception {
        File file = new File(dirPath);
        if (file.isFile()) {
            throw new Exception("dirPath不是文件夹");
        }
        String[] subFileNames = file.list();
        if (subFileNames == null) {
            throw new Exception("subFileNames == null");
        }
        return subFileNames.length == 0;
    }

    /**
     * 判断文件或文件夹是否存在
     */
    public static boolean isExist(String path) {
        return new File(path).exists();
    }


    /**
     * 将bytes数据写入filePath文件中
     * @param bytes
     * @param filePath
     */
    public static void write(byte[] bytes, String filePath) throws Exception {
        File file = new File(filePath);
        /*if(file.exists() && file.isFile()){

        }*/
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }
}
