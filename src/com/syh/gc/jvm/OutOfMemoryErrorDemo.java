package com.syh.gc.jvm;

/**
 * @author hsy
 * @createtime 2019/5/4 15:46
 */

/**
 * OutOfMemoryError:Java heap space 例子
 */
public class OutOfMemoryErrorDemo {
    public static void main(String[] args) {

        String memoryError = "OutOfMemoryError";
        while (true){
            memoryError += memoryError;
        }
    }
}
