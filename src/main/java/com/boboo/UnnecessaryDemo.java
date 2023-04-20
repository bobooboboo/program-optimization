package com.boboo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * UnnecessaryDemo
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
            // 看情况时返回空集合还是null 不要返回new ArrayList<>();浪费内存
            return Collections.emptyList();
//            return null;
        }

        // 最好指定集合的size 避免频繁扩容
        List<Object> result = new ArrayList<>();
        if (RANDOM.nextBoolean()) {
            Object o = new Object();
            result.add(o);
        }
        return result;
    }
}
