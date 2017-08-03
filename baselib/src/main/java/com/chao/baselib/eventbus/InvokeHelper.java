package com.chao.baselib.eventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 方法执行帮助类
 */
public class InvokeHelper {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void post(final SubscriberMethod subscriberMethod, final Object sure, final Object parameter) throws InvocationTargetException, IllegalAccessException {
        switch (subscriberMethod.getThreadMode()) {
            case POSTING:
                //直接执行
                subscriberMethod.getMethod().invoke(sure, parameter);
                break;
            case MAIN:
                if (isMainThread()) {
                    //直接执行
                    subscriberMethod.getMethod().invoke(sure, parameter);
                } else {
                    // 放在handler内执行
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                subscriberMethod.getMethod().invoke(sure, parameter);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case BACKGROUND://后台线程
                if (isMainThread()) {
                    //放在后台线程执行
                    threadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                subscriberMethod.getMethod().invoke(sure, parameter);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    //执行
                    subscriberMethod.getMethod().invoke(sure, parameter);
                }
                break;
            case ASYNC://异步线程
                if (isMainThread()) {
                    threadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                subscriberMethod.getMethod().invoke(sure, parameter);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    subscriberMethod.getMethod().invoke(sure, parameter); //放在异步线程内执行
                }
                break;
            default:
                //抛异常
                throw new IllegalStateException("Unknown thread mode: " + subscriberMethod.getThreadMode());
        }
    }

    public static boolean isMainThread() {
//        return Looper.getMainLooper().getThread() == Thread.currentThread();
//        return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
        return Looper.getMainLooper() == Looper.myLooper();
    }
}