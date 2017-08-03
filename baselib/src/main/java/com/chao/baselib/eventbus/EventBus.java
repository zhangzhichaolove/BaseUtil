package com.chao.baselib.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 自定义EventBus
 * Created by Chao on 2017/6/2.
 */

public class EventBus {
    private static EventBus instance;
    private Map<Object, List<SubscriberMethod>> map;

    public static EventBus getDefault() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    private EventBus() {
        map = new HashMap<>();
    }


    public void register(Object obj) {
        List<SubscriberMethod> list = map.get(obj);
        if (list == null) {
            List<SubscriberMethod> methods = findSubscriber(obj);
            map.put(obj, methods);
        }
    }

    /**
     * 寻找该类方法
     */
    private List<SubscriberMethod> findSubscriber(Object obj) {
        List<SubscriberMethod> list = new CopyOnWriteArrayList<>();
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            Method[] methods = clazz.getDeclaredMethods();//获取该类所有方法
            String name = clazz.getName();
            //跳过系统类
            if (name.startsWith("android.") || name.startsWith("java.") || name.startsWith("javax.")) {
                break;
            }
            for (Method method : methods) {
                // 获取该变量上面有没有打这个注解
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null) {// 无此注解
                    continue;
                }
                Class<?>[] parameterTypes = method.getParameterTypes();//获取参数类型
                if (parameterTypes == null || parameterTypes.length != 1) {//不符合一个参数标准
                    throw new IllegalArgumentException("You must ensure that there is only one parameter");//你必须保证只有一个参数

                }
                ThreadMode threadMode = subscribe.threadMode();// 获取注解值
                SubscriberMethod subscriberMethod = new SubscriberMethod(method, parameterTypes[0], threadMode, false);
                list.add(subscriberMethod);
                //LogUtils.showTagE("TAG", "当前线程:" + Thread.currentThread().getName() + "\t线程指定为 ：" + threadMode + "\t注解方法为：" + method.getName() + "\t" + parameterTypes[0]);
            }
            clazz = clazz.getSuperclass();
        }
        return list;
    }


    public void unregister(Object obj) {
        map.remove(obj);
    }

    public void post(Object obj) {//BaseEvent
        Set<Object> set = map.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            Object mclzz = iterator.next();
            List<SubscriberMethod> list = map.get(mclzz);
            for (SubscriberMethod subscriberMethod : list) {
                if (subscriberMethod.getEventType().isAssignableFrom(obj.getClass())) {
                    invoke(mclzz, subscriberMethod, obj);
                }
            }
        }
//        for (Object o : map.keySet()) {
//            SubscriberMethod method = map.get(o);
//            if (method.getEventType().isAssignableFrom(obj.getClass())) {//参数匹配
//                try {
//                    InvokeHelper.post(method, o, obj);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    private void invoke(Object mclzz, SubscriberMethod subscriberMethod, Object obj) {
        try {
            InvokeHelper.post(subscriberMethod, mclzz, obj);
            //subscriberMethod.getMethod().invoke(mclzz,obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
