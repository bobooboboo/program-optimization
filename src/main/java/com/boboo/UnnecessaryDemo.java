package com.boboo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 非必要对象创建
 *
 * @author: boboo
 * @Date: 2023/4/20 16:19
 **/
public class UnnecessaryDemo {

    public static final Random RANDOM = new Random();
    public static List<Object> demo1() {
        List<Object> result = new ArrayList<>();
        // 某些业务校验
        if (RANDOM.nextBoolean()) {
            return result;
        }

        Object o = new Object();
        if (RANDOM.nextBoolean()) {
            result.add(o);
        }
        return result;
    }

    public static List<Object> fixedDemo1() {
        // 某些业务校验
        if (RANDOM.nextBoolean()) {
            // 看情况时返回空集合还是null 如果需要保证此方法返回的结果只供查询或展示 就不要返回new ArrayList<>()浪费内存
            return Collections.emptyList();
            // return null;
            // 如果返回的结果可以进行add或remove需要返回new ArrayList()
            // return new ArrayList<>();
        }

        // 最好指定集合的size 避免频繁扩容
        List<Object> result = new ArrayList<>(4);
        if (RANDOM.nextBoolean()) {
            Object o = new Object();
            result.add(o);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("freeMemory:" + Runtime.getRuntime().freeMemory());
        for (int i = 0; i < 1000000; i++) {
            List<Object> list =  new ArrayList<>();
        }
        System.out.println("freeMemory:" + Runtime.getRuntime().freeMemory());
        for (int i = 0; i < 1000000; i++) {
            List<Object> list =  Collections.emptyList();
        }
        System.out.println("freeMemory:" + Runtime.getRuntime().freeMemory());
    }
}
