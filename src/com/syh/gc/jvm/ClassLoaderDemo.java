package com.syh.gc.jvm;

import com.sun.java.accessibility.util.EventID;

/**
 * @author hsy
 * @createtime 2019/5/4 13:54
 */

/**
 * JVM几种类加载器
 */
public class ClassLoaderDemo {
    public static void main(String[] args) {
        //1、Bootstrap ClassLoader:启动类加载器
        Object obj = new Object();
        System.out.println("Bootstrap ClassLoader: " + obj.getClass().getClassLoader());


        //2、Extension ClassLoader：扩展类加载器
        EventID eventID = new EventID();
        System.out.println("Extension ClassLoader: " + eventID.getClass().getClassLoader());

        //3、System ClassLoader：系统类加载器
        ClassLoaderDemo classLoaderDemo = new ClassLoaderDemo();
        System.out.println("System ClassLoader: " + classLoaderDemo.getClass().getClassLoader());

        //4、证明继承关系
        System.out.println("System ClassLoader.parent: " + classLoaderDemo.getClass().getClassLoader().getParent());
        System.out.println("System ClassLoader.parent.parent: " + classLoaderDemo.getClass().getClassLoader().getParent().getParent());
    }
}
