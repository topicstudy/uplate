package com.wjh.test;

import com.wjh.util.FileUtil;
import com.wjh.util.UPlateUtil;

import java.io.File;
import java.io.IOException;

public class T2 {
    public static void main(String[] args) throws IOException {
        String p = "e:/";
       // System.out.println(new File(p).list().length);
       // System.out.println(UPlateUtil.estimateTime("E"));
new File("E:\\");
        System.out.println(UPlateUtil.getMerchantLabelCapacity("E"));
    }
}
