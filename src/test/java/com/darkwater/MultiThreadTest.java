package com.darkwater;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by lenovo1 on 2017/2/20.
 */
class MyThread extends Thread {
    private int tid;

    MyThread(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; ++i) {
                System.out.println(String.format("%d:%d", tid, i));
                sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    BlockingQueue<String> q;

    Consumer(BlockingQueue<String> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":" + q.take());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Producer implements Runnable {
    BlockingQueue<String> q;

    Producer(BlockingQueue<String> q){
        this.q = q;
    }
    @Override
    public void run() {
        try {
            for (int i = 0 ; i < 100 ; i++){
                Thread.sleep(1000);
                q.add(String.valueOf(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class MultiThreadTest {
    private static Object obj = new Object();

    public static void testBlockingQueue(){
        BlockingQueue<String> q = new ArrayBlockingQueue<String>(10);//队列的容量
        new Thread(new Producer(q)).start();
        new Thread(new Consumer(q),"Consumer1").start();
        new Thread(new Consumer(q),"Consumer2").start();
    }

    public static void testThread() {
        for (int i = 0; i < 10; i++) {
//            new MyThread(i).start();
        }
        synchronized (obj) {
            for (int i = 0; i < 10; i++) {
                try {
                    new MyThread(i).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
//        testThread();
        testBlockingQueue();
    }
}
