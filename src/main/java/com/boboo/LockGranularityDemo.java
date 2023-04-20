package com.boboo;

import java.util.concurrent.TimeUnit;

/**
 * 锁粒度示例:抢披萨
 *
 * @author: boboo
 * @Date: 2023/4/19 13:14
 **/
@SuppressWarnings("all")
public class LockGranularityDemo {
    /**
     * 20个人
     */
    private static final int PERSON_AMOUNT = 20;

    /**
     * 10块披萨
     */
    private volatile static int PIZZA_AMOUNT = 10;

    /**
     * 洗手
     */
    public static void washHand() {
        try {
            // 模拟洗手耗时
            System.out.println(Thread.currentThread().getName() + "开始洗手");
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (Exception ignored) {

        }
    }

    /**
     * 20个人抢10块披萨
     * 抢披萨前提：必须先洗手
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < PERSON_AMOUNT; i++) {
            new Thread(() -> {
                synchronized (LockGranularityDemo.class) {
                    washHand();
                    if (PIZZA_AMOUNT > 0) {
                        System.out.println(Thread.currentThread().getName() + "抢到了披萨");
                        PIZZA_AMOUNT--;
                    } else {
                        System.out.println(Thread.currentThread().getName() + "来晚了");
                    }
                }
            }, "thread-" + i).start();
        }
        while (PIZZA_AMOUNT > 0) {

        }
        long endTime = System.currentTimeMillis();
        System.out.println("披萨抢完了!本次耗时" + (endTime - startTime) + "ms");
    }
}
