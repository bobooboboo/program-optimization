package com.boboo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 异步示例
 *
 * @author: boboo
 * @Date: 2023/4/20 16:56
 **/
@SuppressWarnings("all")
public class AsyncDemo {

    public static final Random RANDOM = new Random();

    public static final List<String> PLATFORM = Arrays.asList("淘宝", "抖音", "京东", "拼多多");

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        withoutResultTest();
//        withResultTest();
        long endTime = System.currentTimeMillis();
        System.out.println("本次总耗时:" + (endTime - startTime) + "ms");
    }

    /**
     * 主线程不关心结果
     */
    public static void withoutResultTest() {
        System.out.println("开始点菜!");
        try {
            // 模拟点菜操作耗时
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception ignored) {

        }
        System.out.println("结束点菜!");
        order("老王", "鱼香肉丝,红烧茄子,西湖醋鱼");
        System.out.println("已经通知后厨");
    }

    /**
     * 点餐
     *
     * @param to      后厨人员
     * @param content 菜品内容
     */
    public static void order(String to, String content) {
        try {
            System.out.println("向" + to + "发送菜品内容:" + content);
            new Thread(() ->
            {
                try {
                    System.out.println(to + "收到菜品内容！");
                    // 模拟炒菜耗时
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("菜品:" + content + " 烹饪完成");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (Exception ignored) {

        }
    }

    /**
     * 主线程关心结果
     */
    public static void withResultTest() {
        List<CompletableFuture<Integer>> futureList = PLATFORM.stream().map(plaform -> CompletableFuture.supplyAsync(() -> queryPrice(plaform))).collect(Collectors.toList());
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]))
                .whenComplete((v, th) -> {
                    Integer price = futureList.stream().mapToInt(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).min().getAsInt();
                    System.out.println("最低价格是:" + price);
                });
        // 等待所有任务完成
        allOf.join();
    }

    /**
     * 模拟平台价格查询
     *
     * @param platform 平台名称
     */
    public static int queryPrice(String platform) {
        int time = RANDOM.ints(1, 5).findFirst().getAsInt();
        try {
            // 模拟查询价格 1s-5s
            TimeUnit.SECONDS.sleep(time);
        } catch (Exception ignored) {

        }
        // 模拟价格 100-200元
        int price = RANDOM.ints(100, 200).findFirst().getAsInt();
        System.out.println("平台：" + platform + "的价格是：" + price + "元，查询耗时：" + time + "秒");
        return price;
    }

}
