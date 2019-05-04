package com.syh.gc.jvm;

/**
 * @author hsy
 * @createtime 2019/5/4 14:38
 */

/**
 * 栈内存溢出：死循环递归调用
 */
public class StackOverflowErrorDemo {

    public static void main(String[] args) {
        syaHello();
    }

    public static void syaHello() {
        syaHello();
    }
}
