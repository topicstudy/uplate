package com.wjh.util;

import com.wjh.entity.UPlate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 测U盘真实容量的工具类
 * 传输大文件时块大小为16MB时最快（实验得出）
 */
public class UPlateUtil {
    //16MB的块
    public static final byte[] block_16MB = new byte[16 * 1024 * 1024];
    //1MB的块
    public static final byte[] block_1MB = new byte[1 * 1024 * 1024];

    /**
     * 先用16MB的块填充，剩余空间不足16MB，再用1MB的块填充
     *
     * @param plateName 例如 E
     * @return U盘实际容量 单位：GB
     */
    public static double measure(String plateName) throws Exception {
        String platePath = plateName + ":/";
        //clearPlate(plateName);
        double capacity = fillAndGetCapacity(platePath);
        //clearPlate(plateName);
        saveResult(plateName, capacity + "GB");
        System.out.println("capcity="+capacity);
        return capacity;
    }

    /**
     * 填充U盘并获取U盘容量
     *
     * @param platePath 例如 E:/
     * @return U盘容量 单位GB
     */
    private static double fillAndGetCapacity(String platePath) {
        System.out.println("pp="+platePath);
        int count_block_16MB = 0;//填充U盘16MB的块用了几个
        int count_block_1MB = 0;//填充U盘1MB的块用了几个
        double capacity = 0.0;//U盘容量 单位GB
        try {//空间不足：java.io.IOException
            while (true) {
                FileUtil.write(block_16MB, platePath+StringUtil.gerRandomString()+".wjh");
                count_block_16MB++;
            }
        } catch (Exception e1) {
            try {
                while (true) {
                    FileUtil.write(block_1MB, platePath+StringUtil.gerRandomString()+".wjh");
                    count_block_1MB++;
                }
            } catch (Exception e2) {
            }
        } finally {
            //计算容量
            capacity = computeCapacity(count_block_16MB, count_block_1MB);
        }

        return capacity;
    }

    /**
     * 根据填充文件的使用个数计算U盘容量
     *
     * @param count_block_16MB
     * @param count_block_1MB
     * @return U盘容量 单位 GB
     */
    private static double computeCapacity(int count_block_16MB, int count_block_1MB) {
        return (16 * count_block_16MB + 1 * count_block_1MB) / 1024.0;
    }


    /**
     * 清空U盘
     *
     * @param plate 例如 e:/
     */
    static void clearPlate(String plate) {
        FileUtil.delete(plate);
    }

    /**
     * 根据U盘商家所标容量估计测量耗时 单位：分钟
     *
     * @param uplateName 例如 E
     */
    public static Double estimateTime(String uplateName) {
        //计算测试需要的时间 4g的U盘需要11分钟 读卡器时间加倍
        return getMerchantLabelCapacity(uplateName) / 4 * 11;
    }

    /**
     * 获取商家标注的U盘容量
     *
     * @param uplateName 例如 E
     * @return 商家标注的U盘容量 单位GB
     */
    public static double getMerchantLabelCapacity(String uplateName) {
        return new File(uplateName + ":/").getTotalSpace() * 1.0 / 1024 / 1024 / 1024;
    }

    /**
     * 保存测量结果到当前电脑中
     * 如果当前电脑无D盘，则保存到C盘 C:/测U盘真实容量/测量结果.txt
     *
     * @param uplateName   例如 E
     * @param realCapacity U盘真实容量 例如 1.3G
     */
    private static void saveResult(String uplateName, String realCapacity) throws Exception {
        String savePath = "D:/";
        if (!FileUtil.isExist(savePath)) {
            savePath = "C:/";
        }
        if (!FileUtil.isExist(savePath)) {
            System.out.println("电脑中没C盘也没D盘，无法保存测量结果到磁盘");
            return;
        }
        savePath += "测量结果.txt";
        String merchantLabelCapacity = getMerchantLabelCapacity(uplateName + ":/") + "GB";
        String msg = "U盘真实容量是" + realCapacity + "(商家标的容量是" + merchantLabelCapacity + ")";
        FileOutputStream fileOutputStream = new FileOutputStream(savePath);
        fileOutputStream.write(msg.getBytes());
        fileOutputStream.close();

        System.out.println("测量结果保存到 " + savePath);
    }

    /**
     * 检测U盘插入、拔出
     * 不能用return返回插入、拔出的U盘信息，因为需要这个方法一直运行
     */
    public static void uPlateInOrOutListen(UPlate uPlate) {
        //电脑中所有的盘
        File[] allRoots = File.listRoots();

        while (true) {
            ThreadUtil.sleep(500,Thread.currentThread());//为了避免一直检测消耗资源
            //插入、拔出U盘后电脑中所有的盘
            File[] afterUPlateInOrOutAllRoots = File.listRoots();
            //插入U盘
            if (afterUPlateInOrOutAllRoots.length > allRoots.length) {
                //找出哪个盘是插入的U盘
                HashSet<File> set = new HashSet(Arrays.asList(allRoots));
                HashSet<File> set2 = new HashSet(Arrays.asList(afterUPlateInOrOutAllRoots));
                set2.removeAll(set);
                String uPlateName = set2.iterator().next().toString();//E:\
                uPlateName = uPlateName.substring(0, 1);//E
                System.out.println(uPlateName);
                uPlate.setStatus(UPlate.EXIST);
                uPlate.setName(uPlateName);
            } else if (afterUPlateInOrOutAllRoots.length < allRoots.length) {
                uPlate.setStatus(UPlate.NOT_EXIST);
                uPlate.setName("");
            }
            allRoots = File.listRoots();
        }
    }
}
