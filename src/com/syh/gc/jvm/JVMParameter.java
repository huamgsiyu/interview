package com.syh.gc.jvm;

/**
 * @author hsy
 * @createtime 2019/5/4 15:25
 */

/**
 * JVM简单参数调优
 */
public class JVMParameter {
    public static void main(String[] args) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("maxMemory = " + maxMemory/1024/1024 + "MB");

        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("totalMemory = " + totalMemory/1024/1024 + "MB");


    }
}
