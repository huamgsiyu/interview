package com.hsy.lock;

/**
 * @author hsy
 * @createtime 2019/4/26 8:22
 */

import lombok.Getter;

/**
 * 枚举类Demo，可以保存一些不尝变的值，很方便。
 */
public enum EnumTest {
    ONE(1, "第一个"), TWO(2, "第二个"),THREE(3, "第三个");

    private Integer code;
    private String value;

    EnumTest(Integer code, String value)
    {
        this.code = code;
        this.value = value;
    }

    public static EnumTest foreach(int index)
    {
        EnumTest[] myArray = EnumTest.values();
        for (EnumTest enumTest : myArray) {
            if(index == enumTest.getCode())
            {
                return enumTest;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}

class MyEnum
{
    public static void main(String[] args) {
        EnumTest enumTest = EnumTest.foreach(2);
        Integer code = enumTest.getCode();
        System.out.println("code = " + code);
        String value = enumTest.getValue();
        System.out.println("value = " + value);

    }
}
