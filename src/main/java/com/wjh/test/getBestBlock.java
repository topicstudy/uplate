package com.wjh.test;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 将jvm中的数据写入磁盘
 * 块大小多少时最快
 */
public class getBestBlock {
    static byte[][] allArr = {
             new byte[700 * 1024 * 1024],
             new byte[600 * 1024 * 1024],
            new byte[500 * 1024 * 1024],
             new byte[400 * 1024 * 1024],
            new byte[300 * 1024 * 1024],
    };
    //static byte[][] blockArr = new byte[128][];
    //保存最合适的块大小 v "3.00_block=128MB"
    static Map<String, String> bestMap = new HashMap();
    static byte[][] blockArr = {
            new byte[128 * 1024 * 1024],
            new byte[64 * 1024 * 1024],
            new byte[32 * 1024 * 1024],
            new byte[16 * 1024 * 1024],
            new byte[8 * 1024 * 1024],
            new byte[4 * 1024 * 1024],
            new byte[2 * 1024 * 1024],
            new byte[1 * 1024 * 1024],
    };

    static {
      /*  for (int i = 0; i < blockArr.length / 4; i++) {
            blockArr[i] = new byte[(i + 1) * 1024 * 1024];
        }*/
    }

    public static void main(String[] args) {
        write();
        System.out.println(bestMap);
    }

    static void write() {
        for (byte[] all : allArr) {
            for (byte[] block : blockArr) {
                double pieces = Math.ceil(all.length * 1.0 / block.length);
                long t1 = System.nanoTime();
                for (int i = 0; i < pieces; i++) {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream("d:/tmp/t/best_" + i + ".txt");
                        fileOutputStream.write(block);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }//pieces
                long t2 = System.nanoTime();
                double time = (t2 - t1) * 1.0 / 1_000_000_000;//秒
                String allMB = all.length * 1.0 / 1024 / 1024 + "MB";
                String blockMB = block.length * 1.0 / 1024 / 1024 + "MB";
                System.out.println("all=" + allMB
                        + ",block=" + blockMB
                        + ",time=" + time + "秒" + ",p=" + pieces);

                String k = "all=" + allMB;//"all=500MB"
                if (bestMap.containsKey(k)) {
                    String v = bestMap.get(k);
                    String[] ss = v.split("_");
                    double bestTime = Double.parseDouble(ss[0]);
                    if (time < bestTime) {
                        String newV = time + "_block=" + blockMB;
                        bestMap.put(k, newV);
                    }
                } else {
                    bestMap.put(k, time + "_block" + blockMB);
                }
            }//block
        }//all
    }
}
