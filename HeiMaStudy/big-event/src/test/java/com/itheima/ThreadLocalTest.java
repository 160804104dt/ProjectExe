package com.itheima;

import org.junit.jupiter.api.Test;

/**
 * ThreadLocalTest
 *
 * @author dingtao
 * @date 2024/7/27 15:13
 */
public class ThreadLocalTest {

    @Test
    public void TestThreadLocalSetAndGet() {
        //提供一个Thread Local对象
        ThreadLocal tl = new InheritableThreadLocal();

        new Thread(() -> {
            tl.set("丁涛");
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        }, "蓝色").start();

        new Thread(() -> {
            tl.set("廖静");
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        }, "红色").start();
    }
}
