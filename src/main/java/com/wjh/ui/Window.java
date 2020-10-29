package com.wjh.ui;

import com.wjh.entity.UPlate;
import com.wjh.util.Constant;
import com.wjh.util.ThreadUtil;
import com.wjh.util.UPlateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 测U盘真实容量的窗口
 */
public class Window {
    //窗口
    private static final JFrame jFrame = new JFrame();
    private static final Container contentPane = jFrame.getContentPane();
    //U盘信息{status:xx,uPlateName:xx}
    private static UPlate uPlate = new UPlate();
    //U盘插入、拔出信息
    private static JLabel uPlateInOrOutJLabel;
    //开始测量的按钮
    private static JButton startMeasureJButton;
    //错误信息
    private static JLabel errMsgJLabel;
    //测U盘需要的时间
    private static JLabel timeJLabel;
    //正在处理的图标
    private static JLabel ingJLabel;
    //U盘容量
    private static JLabel capacityJLabel;
    //空白字符串，为了使label独占一行
    private static String s = "                                                                                     ";

    static {
        //窗口基本信息
        jFrame.setVisible(true);
        jFrame.setResizable(true);
        jFrame.setLocation(200, 200);
        jFrame.setSize(520, 400);
        jFrame.setTitle(Constant.APP_NAME + Constant.VERSION);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口后关闭程序
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));//布局
        //窗口图标
        String iconRelativePath = Constant.RELATIVE_STATIC_RESOURCE_PATH + "icon.png";
        jFrame.setIconImage(new ImageIcon(iconRelativePath).getImage());
        //test start 为了不频繁插拔U盘
        //uPlate.setName("E");
        //uPlate.setStatus(UPlate.EXIST);
        //test end
    }

    public static void main(String[] args) {
        //添加组件
        //提示信息
        addNormalLabel("①先打开该软件再插入U盘");
        addNormalLabel("②该过程会删除U盘中所有文件(请备份好重要文件)");
        addNormalLabel("③先格式化U盘再测量，结果更准确");
        addNormalLabel("④测量过程电脑会有点卡" + s);
        //开始测试的按钮
        startMeasureJButton = addNormalButton("开始测试");
        //U盘插入、拔出的信息
        uPlateInOrOutJLabel = addDangerLabel("未检测到U盘(若已插入U盘，请拔下重插)");
        //错误信息
        errMsgJLabel = addDangerLabel("");
        // 测U盘需要的时间
        timeJLabel = addNormalLabel("");
        //正在处理的图标
        ingJLabel = addNormalLabel("");
        ingJLabel.setIcon(null);
        //U盘容量
        capacityJLabel = addNormalLabel("");

        //监听U盘插入或拔出
        uPlateInOrOutListen();
        //展示U盘插入、拔出信息
        showUPlateInOrOutInfo();
        //设置按钮的动作（点击）监听器
        setStartMeasureJButtonActionListener(startMeasureJButton);

        contentPane.validate();
    }

    /**
     * 添加label（提示危险信息）
     */
    private static JLabel addDangerLabel(String msg) {
        return addLabel(msg, Color.RED);
    }

    /**
     * 添加label（提示正常信息）
     */
    private static JLabel addNormalLabel(String msg) {
        return addLabel(msg, Constant.MAIN_COLOR);
    }

    /**
     * 添加label（提示信息）
     *
     * @param foreGroundColor Color.RED...
     */
    private static JLabel addLabel(String msg, Color foreGroundColor) {
        JLabel jLabel = new JLabel(msg);
        jLabel.setForeground(foreGroundColor);
        jLabel.setFont(Constant.FONT);
        contentPane.add(jLabel);

        return jLabel;
    }

    /**
     * 添加button
     */
    private static JButton addNormalButton(String msg) {
        JButton jButton = new JButton(msg);
        contentPane.add(jButton);
        return jButton;
    }

    /**
     * 监听U盘插入、拔出
     */
    private static void uPlateInOrOutListen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UPlateUtil.uPlateInOrOutListen(uPlate);
            }
        }).start();
    }

    /**
     * 在窗口中显有无U盘
     */
    private static void showUPlateInOrOutInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);//节约资源
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String status = uPlate.getStatus();
                    String uPlateName = uPlate.getName();

                    if ((UPlate.EXIST).equals(status)) {//有U盘
                        uPlateInOrOutJLabel.setText("检测到U盘：" + uPlateName);
                        errMsgJLabel.setText("");
                    } else if ((UPlate.NOT_EXIST).equals(status)) {//无U盘
                        uPlateInOrOutJLabel.setText("未检测到U盘");
                        capacityJLabel.setText("");
                        timeJLabel.setText("");
                        ingJLabel.setIcon(null);
                    }
                }
            }
        }).start();
    }

    /**
     * 给开始测量U盘容量的按钮设置动作监听器
     */
    private static void setStartMeasureJButtonActionListener(final JButton startMeasureJButton) {
        startMeasureJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String status = uPlate.getStatus();
                final String uPlateName = uPlate.getName();
                if ((UPlate.NOT_EXIST).equals(status)) {//无U盘
                    errMsgJLabel.setText("未检测到U盘(若已插入U盘，请拔下重插)");
                    return;
                }
                //隐藏"开始测量"的按钮
                startMeasureJButton.setVisible(false);
                //影藏结果
                capacityJLabel.setText("");

                //预计测该U盘需要的时间 单位：分钟
                double time = UPlateUtil.estimateTime(uPlateName);
                timeJLabel.setText("正在测量...预计耗时：" + String.format("%.2f", time) + " 分钟(若是读卡器时间加倍)                                        ");
                ingJLabel.setIcon(new ImageIcon(Constant.RELATIVE_STATIC_RESOURCE_PATH + "ing.gif"));

                final Double[] gBytes = {null};//测量结果
                //为什么要开个线程：测U盘容量很耗时，防止主线程阻死
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            gBytes[0] = UPlateUtil.measure(uPlateName);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }).start();

                //测量完成
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            //System.out.println("gBytes[0]="+gBytes[0]);//这句能使线程sleep
                            ThreadUtil.sleep(500, Thread.currentThread());//必须使线程sleep否则结果不正确
                            if (gBytes[0] != null) {
                                //System.out.println("gBytes[0] != null");
                                //显示测量结果
                                capacityJLabel.setText("该U盘的实际容量是：" + gBytes[0] + " GB                            ");
                                //去掉正在处理的图标
                                ingJLabel.setIcon(null);
                                //展示"开始测量"的按钮
                                startMeasureJButton.setVisible(true);
                                return;
                            }
                        }
                    }
                }).start();
            }
        });
    }
}
